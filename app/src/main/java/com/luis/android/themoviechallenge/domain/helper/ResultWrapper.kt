package com.luis.android.themoviechallenge.domain.helper

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class RequestError(val code: Int? = null, val error: String? = null): ResultWrapper<Nothing>()
}
