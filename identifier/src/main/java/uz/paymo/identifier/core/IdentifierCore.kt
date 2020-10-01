/*
 * Copyright (c) Siyavushkhon Kholmatov, 27/9/2020 at 18:54
 */

package uz.paymo.identifier.core

import android.content.Intent
import android.graphics.Bitmap
import uz.paymo.identifier.IdentificationListener

/**
 * Identifier's helper methods interface
 */
internal interface IdentifierCore {
    /**
     * [IdentificationListener] with callbacks of the result
     */
    var identificationListener: IdentificationListener?

    /**
     * Request code is used in startActivityForResult for PAYMO.ID application
     * [DEFAULT_REQUEST_CODE] = 11273
     * Change depending on your own needs
     */
    var requestCode: Int

    /**
     * Check if the PAYMO.ID application is installed
     * @return true/false whether the PAYMO.ID app is installed or not
     */
    fun coreAppInstalled(): Boolean

    /**
     * Check whether device supports PAYMO.ID by MIN_SDK
     * OPTIONAL: You can also check for the Hardware NFC support with [hasHardwareNFCSupport]
     * @return [true] if supports and [false] otherwise
     */
    fun hasSoftwareSupport(): Boolean

    /**
     * Check whether device supports PAYMO.ID's NFC feature
     * NOTE: It is not required to support Hardware NFC
     * but, Software support is the most important one. See [hasSoftwareSupport]
     * @return [true] if supports and [false] otherwise
     */
    fun hasHardwareNFCSupport(): Boolean

    /**
     * Converts base64 string based photo to android [Bitmap]
     * @param base64 encoded string
     * @return decoded [Bitmap] image from [base64]
     */
    fun base64ToBitmap(base64: String): Bitmap

    /**
     * Converts base64 string based photo to [ByteArray]
     * @param base64 encoded string
     * @return decoded [ByteArray] image from [base64]
     */
    fun base64ToByteArray(base64: String): ByteArray

    /**
     * Start Identification process in PAYMO.ID
     * @param agentId Unique ID for the 3rd party service given by PAYMO
     * @param apiKey key for specific agent given by PAYMO
     * @throws SoftwareSupportException when device does not support by its MIN SDK.
     * You may check using [hasSoftwareSupport]
     */
    fun requestIdentification(agentId: Int, apiKey: String)

    /**
     * Initialize the result data from PAYMO.ID.
     * Call the function inside [AppCompatActivity.onActivityResult] or [Fragment.onActivityResult]
     * Pass all the parameters
     * @param requestCode Check the [requestCode] to set the custom code for requesting
     * @param resultCode
     * @param data
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}