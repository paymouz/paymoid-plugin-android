/*
 * Copyright (c) Siyavushkhon Kholmatov, 26/9/2020 at 22:21
 */

package uz.paymo.identifier.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.nfc.NfcAdapter
import android.os.Build
import android.util.Base64
import uz.paymo.identifier.BuildConfig
import java.security.MessageDigest


internal class Utils {
    internal companion object {
        fun hasSoftwareSupport(): Boolean = Build.VERSION.SDK_INT >= BuildConfig.PAYMO_ID_MIN_SDK
        fun hasHardwareNfcSupport(context: Context): Boolean {
            return try {
                hasSoftwareSupport() && NfcAdapter.getDefaultAdapter(context) != null
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        fun md5(toEncrypt: String): String {
            return try {
                val digest = MessageDigest.getInstance("md5")
                digest.update(toEncrypt.toByteArray())
                val bytes = digest.digest()
                val sb = StringBuilder()
                for (i in bytes.indices) {
                    sb.append(String.format("%02X", bytes[i]))
                }
                sb.toString().toLowerCase()
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        fun base64PhotoToBytes(encodedHash: String?): ByteArray =
            Base64.decode(encodedHash ?: "", Base64.DEFAULT)

        fun base64PhotoToBitmap(encodedHash: String?): Bitmap {
            val bytes = base64PhotoToBytes(encodedHash)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size);
        }
    }
}