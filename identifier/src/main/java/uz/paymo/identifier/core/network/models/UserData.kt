/*
 * Copyright (c) Siyavushkhon Kholmatov, 01/10/2020 at 19:14
 */

package uz.paymo.identifier.core.network.models

import com.google.gson.annotations.SerializedName

data class UserData(
    /**
     * Unique ID of identification process
     */
    @SerializedName("id")
    val id: Long,

    /**
     * Auth key defining Agent for identification
     */
    @SerializedName("auth_key")
    val authKey: String,
    @SerializedName("ip")
    val ipAddress: String,

    /**
     * IP address of device
     */
    @SerializedName("user_agent")
    val userAgent: String,
    @SerializedName("device")
    val deviceInfo: String,

    /**
     * Device user agent during identification
     */
    @SerializedName("doc_number")
    val passportNumber: String,
    @SerializedName("pin")
    val pin: String,

    /**
     * Device information
     */
    @SerializedName("first_name")
    val firstName: String,

    /**
     * User passport number
     */
    @SerializedName("last_name")
    val lastName: String,

    /**
     * Passport's person identification number
     */
    @SerializedName("birth_date")
    val dateOfBirth: String,

    /**
     * First name in passport
     */
    @SerializedName("issue_date")
    val dateOfIssue: String,

    /**
     * Last name in passport
     */
    @SerializedName("expiry_date")
    val dateOfExpiry: String,

    /**
     * Date of birth in passport
     */
    @SerializedName("gender")
    val gender: String,

    /**
     * Date of issue in passport
     */
    @SerializedName("country")
    val nationality: String,

    /**
     * Date of expiry in passport
     */
    @SerializedName("photo")
    val photoBase64: String,

    /**
     * Gender in passport
     */
    @SerializedName("doc_type")
    val documentType: String,

    /**
     * Nationality in passport
     */
    @SerializedName("authority")
    val issuerCentre: String,

    /**
     * State of identification process
     * [state] = 1 if identification passed successful, 0 otherwise
     */
    @SerializedName("state")
    val state: Int = 0
)