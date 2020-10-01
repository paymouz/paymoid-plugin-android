/*
 * Copyright (c) Siyavushkhon Kholmatov, 30/9/2020 at 23:20
 */

package uz.paymo.identifier.core.network.base

import retrofit2.Response

/**
 * Safely executes API Request by categorising of ApiResult<K> type
 * @param T API Request Interface that calls [apiRequest]
 * @param D Actual DATA From API Response which should be wrapped in BaseModel
 * @param K BaseModel object that wraps Data as BaseModel<D>
 */
internal suspend fun <T, D, K : BaseModel<D>> T.safeExecute(apiRequest: suspend T.() -> Response<K>): ApiResult<K> {
    return try {
        val response = this.apiRequest()
        when {
            response.isSuccessful && response.body()?.result?.code == "OK" -> {
                ApiResult.Success(response.body()!!)
            }
            response.body()?.result?.code != "OK" -> {
                ApiResult.Failure(response.body()!!.result)
            }
            else ->
                ApiResult.Error(
                    message = response.errorBody()?.string(),
                    throwable = IllegalAccessException(
                        "Retrofit response is not successful\n${response.errorBody().toString()}"
                    )
                )
        }
    } catch (exception: Exception) {
        ApiResult.Error(throwable = exception)
    }
}


internal suspend fun <D, T : ApiResult<D>> T.processResponse(
    onSuccess: suspend (data: D) -> Unit,
    onFailure: suspend (resultModel: ResultModel) -> Unit,
    onError: suspend (msg: String?, throwable: Throwable) -> Unit,
) {
    when (this) {
        is ApiResult.Success<*> -> onSuccess.invoke(data as D)
        is ApiResult.Failure -> onFailure.invoke(result)
        is ApiResult.Error -> onError.invoke(message, throwable)
    }
}