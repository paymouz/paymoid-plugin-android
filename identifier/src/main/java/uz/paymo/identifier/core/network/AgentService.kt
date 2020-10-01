package uz.paymo.identifier.core.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import uz.paymo.identifier.core.network.base.BaseModel
import uz.paymo.identifier.core.network.models.AgentLogin
import uz.paymo.identifier.core.network.models.AgentLoginRequest
import uz.paymo.identifier.core.network.models.UserData

internal interface AgentService {
    @PUT("/agent/login")
    suspend fun loginAgent(
        @Body request: AgentLoginRequest
    ): Response<BaseModel<AgentLogin>>

    @GET("/agent/auth/{agent_id}/{auth_key}/{sign}")
    suspend fun getData(
        @Path("agent_id") agentId: Int,
        @Path("auth_key") authKey: String,
        @Path("sign") signSalt: String
    ): Response<BaseModel<UserData>>
}