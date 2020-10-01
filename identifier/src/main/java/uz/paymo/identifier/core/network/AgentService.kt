package uz.paymo.identifier.core.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import uz.paymo.identifier.core.network.base.BaseModel
import uz.paymo.identifier.core.network.models.AgentLogin
import uz.paymo.identifier.core.network.models.AgentLoginRequest

internal interface AgentService {
    @PUT("/agent/login")
    suspend fun loginAgent(
        @Body request: AgentLoginRequest
    ): Response<BaseModel<AgentLogin>>
}