/*
 * Copyright (c) Siyavushkhon Kholmatov, 01/10/2020 at 19:14
 */

package uz.paymo.identifier.core.network.models

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id")
    val id: Long,
    @SerializedName("auth_key")
    val authKey: String,
    @SerializedName("ip")
    val ipAddress: String,
    @SerializedName("user_agent")
    val userAgent: String,
    @SerializedName("device")
    val deviceInfo: String,
    @SerializedName("doc_number")
    val passportNumber: String,
    @SerializedName("pin")
    val pin: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("birth_date")
    val dateOfBirth: String,
    @SerializedName("issue_date")
    val dateOfIssue: String,
    @SerializedName("expiry_date")
    val dateOfExpiry: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("country")
    val nationality: String,
    @SerializedName("photo")
    val photoBase64: String,
    @SerializedName("doc_type")
    val documentType: String,
    @SerializedName("authority")
    val issuerCentre: String,
    @SerializedName("state")
    val state: Int = 0
)