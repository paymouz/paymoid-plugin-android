/*
 * Copyright (c) Siyavushkhon Kholmatov, 26/9/2020 at 22:21
 */

package uz.paymo.identifier.core

import android.content.Context
import android.nfc.NfcAdapter
import android.os.Build
import uz.paymo.identifier.BuildConfig


internal class Utils {
    companion object {
        fun hasSoftwareSupport(): Boolean = Build.VERSION.SDK_INT >= BuildConfig.PAYMO_ID_MIN_SDK
        fun hasHardwareNfcSupport(context: Context): Boolean {
            return try {
                hasSoftwareSupport() && NfcAdapter.getDefaultAdapter(context) != null
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

    }
}