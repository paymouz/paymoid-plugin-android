/*
 * Copyright (c) Siyavushkhon Kholmatov, 29/9/2020 at 22:36
 */

package uz.paymo.identifier.core

import com.google.gson.annotations.SerializedName
import uz.paymo.identifier.core.network.models.UserPassport

data class IdentificationData(
    /**
     * Success status of Passport Identification process
     * [true] if user has passed successfully, and [false] otherwise
     */
    val success: Boolean,

    /**
     * Unique ID of identification process
     */
    val id: Long,

    /**
     * Agent ID used for identification process
     */
    val agentId : Int,

    /**
     * Auth key defining Agent for identification
     */
    val authKey: String,

    /**
     * IP address of device
     * NOTE: if [success] is false, [ipAddress] will be empty
     */
    val ipAddress: String = "",

    /**
     * Device user agent during identification
     * NOTE: if [success] is false, [userAgent] will be empty
     */
    @SerializedName("user_agent")
    val userAgent: String = "",

    /**
     * Device information
     * NOTE: if [success] is false, [deviceInfo] will be empty
     */
    @SerializedName("device")
    val deviceInfo: String = "",

    /**
     * Person's face photo used for identification
     * NOTE: if [success] is false, [identifiedPhotoBase64] will be empty
     * NB: Person's passport face photo is stored in [UserPassport.photoBase64]
     */
    @SerializedName("photo")
    val identifiedPhotoBase64 : String = "",

    /**
     * Person's passport data from PAYMO.ID
     * NOTE: if [success] is false, [userPassport] will be null since no data passed
     */
    val userPassport: UserPassport? = null
)