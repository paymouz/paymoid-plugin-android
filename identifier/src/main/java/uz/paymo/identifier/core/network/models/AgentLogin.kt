package uz.paymo.identifier.core.network.models

import com.google.gson.annotations.SerializedName
import uz.paymo.identifier.core.utils.Utils

internal data class AgentLogin(
    @SerializedName("auth_key")
    val authKey: String,
    @SerializedName("redirect_url")
    val redirectUrl: String
)

internal data class AgentLoginRequest(
    @SerializedName("agent_id")
    val agentId: Int,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("sign")
    val signSalt: String
)

internal fun makeAgentLoginRequest(agentId: Int, apiKey: String): AgentLoginRequest {
    val timestamp = System.currentTimeMillis() / 1000
    val salt = Utils.md5("$agentId$timestamp$apiKey")
    return AgentLoginRequest(
        agentId = agentId,
        timestamp = timestamp,
        signSalt = salt
    )
}