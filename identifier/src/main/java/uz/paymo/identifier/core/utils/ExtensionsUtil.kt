/*
 * Copyright (c) Siyavushkhon Kholmatov, 27/9/2020 at 13:41
 */

package uz.paymo.identifier.core.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import uz.paymo.identifier.BuildConfig
import uz.paymo.identifier.core.IdentificationData
import uz.paymo.identifier.core.network.models.IdentificationResult

internal const val PLAYSTORE_LINK = "https://play.google.com/store/apps/details?id="
internal const val IDENTIFICATION_DEEPLINK = "paymo-id://agent/oauth/"

internal fun Activity.getCoreAppIntent(): Intent? {
    val intent = this.packageManager?.getLaunchIntentForPackage(BuildConfig.PAYMO_ID_PACKAGE)
    intent?.flags = 0
    return intent
}

internal fun Activity.coreAppInstalled(): Boolean = (this.getCoreAppIntent() != null)

internal fun Intent.setAuthKey(authKey: String) {
    this.data = Uri.parse(IDENTIFICATION_DEEPLINK + authKey)
}

internal fun IdentificationResult.parseToIdentificationData() : IdentificationData {
    val success = state == 1
    return IdentificationData(
        success = success,
        id = id,
        agentId= agentId,
        authKey = authKey,
        ipAddress = ipAddress ?: "",
        userAgent = userAgent ?: "",
        deviceInfo = deviceInfo ?: "",
        identifiedPhotoBase64 = identificationPhoto ?: "",
        userPassport = userPassport
    )
}