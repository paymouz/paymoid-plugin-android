/*
 * Copyright (c) Siyavushkhon Kholmatov, 01/10/2020 at 19:20
 */

package uz.paymo.identifier.core

import com.google.gson.annotations.SerializedName

internal data class PluginCallbackResult(
    @SerializedName("success")
    val success: Boolean,
    /**
     * AuthKey required for 3rd party app to get Passport Data information
     */
    @SerializedName("auth_key")
    val authKey: String
)