package uz.paymo.identifier.core.network.models

import com.google.gson.annotations.SerializedName

internal data class IdentificationResult(
    @SerializedName("id")
    val id: Long,
    @SerializedName("auth_key")
    val authKey: String,
    @SerializedName("agent_id")
    val agentId: Int,
    @SerializedName("ip")
    val ipAddress: String?,
    @SerializedName("user_agent")
    val userAgent: String?,
    @SerializedName("device")
    val deviceInfo: String?,
    @SerializedName("photo")
    val identificationPhoto : String?,
    @SerializedName("state")
    val state : Int,
    @SerializedName("person")
    val userPassport : UserPassport?
)