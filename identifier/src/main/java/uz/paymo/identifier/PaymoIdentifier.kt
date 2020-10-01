/*
 * Copyright (c) Siyavushkhon Kholmatov, 26/9/2020 at 22:14
 */

package uz.paymo.identifier

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import uz.paymo.identifier.core.IdentificationData
import uz.paymo.identifier.core.IdentifierCore
import uz.paymo.identifier.core.PluginCallbackResult
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
    private var agentId: Int? = null
    private var agentApiKey: String? = null
    private var repository = IdentifierRepository()

    override var identificationListener: IdentificationListener? = null
    override var requestCode: Int = DEFAULT_REQUEST_CODE

    override fun coreAppInstalled(): Boolean = activity.coreAppInstalled()
    override fun hasSoftwareSupport(): Boolean = Utils.hasSoftwareSupport()
    override fun hasHardwareNFCSupport(): Boolean = Utils.hasHardwareNfcSupport(context)
    override fun base64ToBitmap(base64: String): Bitmap = Utils.base64PhotoToBitmap(base64)
    override fun base64ToByteArray(base64: String): ByteArray = Utils.base64PhotoToBytes(base64)

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun requestIdentification(agentId: Int, apiKey: String) {
        when {
            !coreAppInstalled() -> showInstallCorePromo()
            !hasSoftwareSupport() -> throw SoftwareSupportException()
            else -> {
                this.agentId = agentId
                this.agentApiKey = apiKey
                initAgentLogin(makeAgentLoginRequest(agentId, apiKey))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == this.requestCode) {
            when (resultCode) {
                RESULT_OK -> {
                    val jsonData = data?.extras?.getString("data")
                    if (jsonData != null) {
                        val callbackResult =
                            Gson().fromJson(jsonData, PluginCallbackResult::class.java)
                        if (!callbackResult.success)
                            identificationListener?.onIdentificationComplete(
                                IdentificationData(false)
                            )
                        else
                            retrieveIdentificationData(callbackResult.authKey)
                    } else
                        identificationListener?.onIdentificationCancel()
                }
                RESULT_CANCELED -> identificationListener?.onIdentificationCancel()
            }
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private fun onConnectionError(throwable: Throwable) {
        identificationListener?.onIdentificationFailed(throwable)
    }

    private fun initAgentLogin(agentLogin: AgentLoginRequest) {
        repository.agentRequest(
            loadingBottomSheet = LoadingBottomSheet(activity),
            apiRequest = { loginAgent(agentLogin) },
            onSuccess = { response ->
                startPaymoID(response.data)
            },
            onFailure = this::onConnectionError,
            onError = this::onConnectionError
        )
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

    private fun retrieveIdentificationData(authKey: String) {
        if (agentId == null || agentApiKey == null) {
            onConnectionError(
                PAYMOIDConnectionError(
                    "AgentID and ApiKey cannot be null. But found agentId=$agentId, apiKey=$agentApiKey"
                )
            )
            return
        }
        val salt = Utils.md5("$agentId$authKey$agentApiKey")
        repository.agentRequest(
            loadingBottomSheet = LoadingBottomSheet(activity),
            apiRequest = { getData(agentId!!, authKey, salt) },
            onSuccess = { response ->
                val success = response.data.state == 1
                val identificationData = IdentificationData(
                    success = success,
                    userData = if (success) response.data else null
                )
                identificationListener?.onIdentificationComplete(identificationData)
            },
            onFailure = this::onConnectionError,
            onError = this::onConnectionError
        )
    }

    companion object {
        private const val DEFAULT_REQUEST_CODE = 11273

        /**
         * Creates an instance of Paymo Identifier for PAYMO.ID
         * @param activity source of the activity ([AppCompatActivity]) from where it is being called
         * @return Instance for the [PaymoIdentifier]
         */
        @JvmStatic
        fun getInstance(appCompatActivity: AppCompatActivity): PaymoIdentifier =
            PaymoIdentifier(appCompatActivity)

        /**
         * Creates an instance of Paymo Identifier for PAYMO.ID
         * @param fragment source of the fragment ([androidx.fragment.app.Fragment]) from where it is being called
         * @return Instance for the [PaymoIdentifier]
         * @throws IllegalArgumentException when [Fragment.getActivity] is null
         */
        @JvmStatic
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