/*
 * Copyright (c) Siyavushkhon Kholmatov, 26/9/2020 at 22:14
 */

package uz.paymo.identifier

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.coroutines.*
import uz.paymo.identifier.core.IdentificationData
import uz.paymo.identifier.core.IdentifierCore
import uz.paymo.identifier.core.network.RetrofitService
import uz.paymo.identifier.core.network.base.processResponse
import uz.paymo.identifier.core.network.base.safeExecute
import uz.paymo.identifier.core.network.models.AgentLogin
import uz.paymo.identifier.core.network.models.AgentLoginRequest
import uz.paymo.identifier.core.network.models.makeAgentLoginRequest
import uz.paymo.identifier.core.utils.*
import uz.paymo.identifier.ui.InstallAppPromoBottomSheet
import uz.paymo.identifier.ui.LoadingBottomSheet

/**
 * Helper Identifier class for PAYMO.ID app
 */
class PaymoIdentifier private constructor(
    private val activity: AppCompatActivity,
    private val fragment: Fragment? = null
) : IdentifierCore {

    private val context: Context = activity.baseContext
    override var identificationListener: IdentificationListener? = null
    override var requestCode: Int = DEFAULT_REQUEST_CODE

    override fun coreAppInstalled(): Boolean = activity.coreAppInstalled()
    override fun hasSoftwareSupport(): Boolean = Utils.hasSoftwareSupport()
    override fun hasHardwareNFCSupport(): Boolean = Utils.hasHardwareNfcSupport(context)

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun requestIdentification(agentId: Int, apiKey: String) {
        when {
            !coreAppInstalled() -> showInstallCorePromo()
            !hasSoftwareSupport() -> throw SoftwareSupportException()
            else -> initAgentLogin(makeAgentLoginRequest(agentId, apiKey))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == this.requestCode) {
            when (resultCode) {
                RESULT_OK -> {
                    val jsonData = data?.extras?.getString("data")
                    if (jsonData != null) {
                        val identificationData =
                            Gson().fromJson(jsonData, IdentificationData::class.java)
                        identificationListener?.onIdentificationComplete(identificationData)
                    } else
                        identificationListener?.onIdentificationCancel()
                }
                RESULT_CANCELED -> identificationListener?.onIdentificationCancel()
            }
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private fun initAgentLogin(agentLogin: AgentLoginRequest) {
        val loadingBottomSheet = LoadingBottomSheet(activity)
        loadingBottomSheet.show()
        CoroutineScope(Dispatchers.IO).launch {
            loadingBottomSheet.onCancel = { this.cancel() }
            val response = RetrofitService.agentService.safeExecute {
                loginAgent(agentLogin)
            }
            withContext(Dispatchers.Main) {
                response.processResponse(
                    onSuccess = { data ->
                        loadingBottomSheet.dismiss()
                        startPaymoID(data.data)
                    },
                    onFailure = {
                        loadingBottomSheet.setError(it.status)
                    },
                    onError = { msg, throwable ->
                        throwable.printStackTrace()
                        loadingBottomSheet.setError(null)
                    }
                )
            }
        }
    }

    private fun showInstallCorePromo() {
        val promoSheet = InstallAppPromoBottomSheet(
            context = activity,
            onCancel = { identificationListener?.onPaymoIDInstallationCancel() },
            onInstall = { openPlayStore() }
        )
        promoSheet.show()
    }

    private fun openPlayStore() {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(PLAYSTORE_LINK + BuildConfig.PAYMO_ID_PACKAGE)
        )
        activity.startActivity(intent)
    }

    private fun startPaymoID(agentLogin: AgentLogin) {
        val intent = activity.getCoreAppIntent()!!
        intent.setAuthKey(agentLogin.authKey)
        if (fragment != null) fragment.startActivityForResult(intent, requestCode)
        else activity.startActivityForResult(intent, requestCode)
    }

    companion object {
        private const val DEFAULT_REQUEST_CODE = 11273

        /**
         * Creates an instance of Paymo Identifier for PAYMO.ID
         * @param activity source of the activity ([AppCompatActivity]) from where it is being called
         * @return Instance for the [PaymoIdentifier]
         */
        fun getInstance(appCompatActivity: AppCompatActivity): PaymoIdentifier =
            PaymoIdentifier(appCompatActivity)

        /**
         * Creates an instance of Paymo Identifier for PAYMO.ID
         * @param fragment source of the fragment ([androidx.fragment.app.Fragment]) from where it is being called
         * @return Instance for the [PaymoIdentifier]
         * @throws IllegalArgumentException when [Fragment.getActivity] is null
         */
        fun getInstance(fragment: Fragment): PaymoIdentifier {
            if (fragment.activity == null)
                throw IllegalArgumentException("Fragment's Activity cannot be null")
            return PaymoIdentifier(
                activity = fragment.activity as AppCompatActivity,
                fragment = fragment
            )
        }
    }

}