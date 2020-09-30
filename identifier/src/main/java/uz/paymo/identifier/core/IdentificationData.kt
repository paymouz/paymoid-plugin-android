/*
 * Copyright (c) Siyavushkhon Kholmatov, 29/9/2020 at 22:36
 */

package uz.paymo.identifier.core

import com.google.gson.annotations.SerializedName

data class IdentificationData(
    /**
     * Success status of Passport Identification process
     * [true] if user has passed successfully, and [false] otherwise
     */
    @SerializedName("success")
    val success: Boolean,

    /**
     * AuthKey required for 3rd party app to get Passport Data information
     */
    @SerializedName("auth_key")
    val authKey: String
)