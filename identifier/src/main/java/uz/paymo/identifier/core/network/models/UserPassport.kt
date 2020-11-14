/*
 * Copyright (c) Siyavushkhon Kholmatov, 01/10/2020 at 19:14
 */

package uz.paymo.identifier.core.network.models

import com.google.gson.annotations.SerializedName

data class UserPassport(
    /**
     * User passport number
     */
    @SerializedName("doc_number")
    val passportNumber: String,

    /**
     * Passport's person identification number
     */
    @SerializedName("pin")
    val pin: String,

    /**
     * First name in passport
     */
    @SerializedName("first_name")
    val firstName: String,

    /**
     * Last name in passport
     */
    @SerializedName("last_name")
    val lastName: String,

    /**
     * Date of birth in passport
     */
    @SerializedName("birth_date")
    val dateOfBirth: String,

    /**
     * Date of issue in passport
     */
    @SerializedName("issue_date")
    val dateOfIssue: String,

    /**
     * Date of expiry in passport
     */
    @SerializedName("expiry_date")
    val dateOfExpiry: String,

    /**
     * Gender in passport
     */
    @SerializedName("gender")
    val gender: String,

    /**
     * Nationality in passport
     */
    @SerializedName("country")
    val nationality: String,

    /**
     * Person's place of birth 
     */
    @SerializedName("place_of_birth")
    val placeOfBirth : String,

    /**
     * Person's photo in passport
     */
    @SerializedName("photo")
    val photoBase64: String,

    /**
     * Document type (for passport 'P')
     */
    @SerializedName("doc_type")
    val documentType: String,

    /**
     * Issuer centre where passport was given
     */
    @SerializedName("authority")
    val issuerCentre: String,
)