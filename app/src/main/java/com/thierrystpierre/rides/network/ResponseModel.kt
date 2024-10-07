package com.thierrystpierre.rides.network

import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel<R>(
    val status: String = "",
    val code: Int = 0,
    val data: R? = null
)

@Serializable
data class ResponseErrorModel(
    val status: String = "",
    val code: Int = 0,
    val error: ResponseError? = null
)

@Serializable
data class ResponseError(
    val message : String = "",
    val detail: String? = null
)