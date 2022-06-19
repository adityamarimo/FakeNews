package com.moja.core.api

import com.moja.core.exception.Failure
import com.moja.core.vo.Either
import retrofit2.Response

interface ApiRequest {
    suspend fun <T> request(apiCall: suspend () -> Response<T>) : Either<Failure, T>
}