package uz.paymo.identifier

import kotlinx.coroutines.*
import retrofit2.Response
import uz.paymo.identifier.core.network.AgentService
import uz.paymo.identifier.core.network.RetrofitService
import uz.paymo.identifier.core.network.base.ApiResult
import uz.paymo.identifier.core.network.base.BaseModel
import uz.paymo.identifier.core.network.base.safeExecute
import uz.paymo.identifier.core.utils.PAYMOIDConnectionError
import uz.paymo.identifier.ui.LoadingBottomSheet

internal class IdentifierRepository {

    private val agentService = RetrofitService.agentService

    internal fun <T> agentRequest(
        loadingBottomSheet: LoadingBottomSheet,
        apiRequest: suspend AgentService.() -> Response<BaseModel<T>>,
        onSuccess: (BaseModel<T>) -> Unit,
        onFailure: ((Throwable) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        loadingBottomSheet.show()
        CoroutineScope(Dispatchers.IO).launch {
            loadingBottomSheet.onCancel = { this.cancel("Loading cancelled") }
            val response = agentService.safeExecute {
                agentService.apiRequest()
            }
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResult.Success -> {
                        loadingBottomSheet.dismiss()
                        onSuccess.invoke(response.data)
                    }
                    is ApiResult.Failure -> {
                        loadingBottomSheet.setError(response.result.status)
                        onFailure?.invoke(
                            PAYMOIDConnectionError(response.result.status)
                        )
                    }
                    is ApiResult.Error -> {
                        response.throwable.printStackTrace()
                        loadingBottomSheet.setError(null)
                        onError?.invoke(response.throwable)
                    }
                }
            }
        }
    }
}