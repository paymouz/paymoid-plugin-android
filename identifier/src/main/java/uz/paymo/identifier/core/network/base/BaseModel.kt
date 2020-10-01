/*
 * Copyright (c) Siyavushkhon Kholmatov, 22/12/2019 at 2:0
 */

package uz.paymo.identifier.core.network.base

import com.google.gson.annotations.SerializedName

internal data class BaseModel<T : Any?>(
    @SerializedName("result") val result: ResultModel,
    @SerializedName("data") val data: T
)

internal data class ResultModel(
    @SerializedName("code") val code: String,
    @SerializedName("status") val status: String
)