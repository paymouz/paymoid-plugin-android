/*
 * Copyright (c) Siyavushkhon Kholmatov, 30/9/2020 at 23:21
 */

package uz.paymo.identifier.core.network.base

internal sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data : T) : ApiResult<T>()
    data class Failure(val result : ResultModel) : ApiResult<Nothing>()
    data class Error(val message : String? = null, val throwable: Throwable) : ApiResult<Nothing>()
}

