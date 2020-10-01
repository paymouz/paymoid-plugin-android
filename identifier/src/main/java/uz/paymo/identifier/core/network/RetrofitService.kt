package uz.paymo.identifier.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.paymo.identifier.BuildConfig

internal object RetrofitService {
    private val instance: Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.PAYMO_ID_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val agentService: AgentService by lazy {
        instance.create(AgentService::class.java)
    }
}