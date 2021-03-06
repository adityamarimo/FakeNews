package com.moja.core.api

import com.moja.core.exception.Failure
import com.moja.core.exception.RequestsResult
import com.moja.core.extension.hasEmptyBody
import com.moja.core.extension.isTotallySuccess
import com.moja.core.vo.Either
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class SafeApiRequest : ApiRequest {

    override suspend fun <T> request(apiCall: suspend () -> Response<T>): Either<Failure, T> {
        return try {
            val response = apiCall.invoke()

            when {
                response.isTotallySuccess() -> Either.Success(response.body()!!)
                response.hasEmptyBody() -> parseError(
                    Failure(response.code(), Throwable(response.message()))
                )
                response.code() in 300..599 -> parseError(
                    Failure(response.code(), Throwable(response.message()))
                )
                else -> parseError(
                    Failure(response.code(), Throwable(response.message()))
                )
            }
        } catch (throwable: Throwable) {
            when(throwable) {
                is UnknownHostException -> parseError(
                    Failure(RequestsResult.NO_CONNECTION, throwable)
                )
                is ConnectException -> parseError(
                    Failure(RequestsResult.NO_CONNECTION, throwable)
                )
                is SocketTimeoutException -> parseError(
                    Failure(RequestsResult.TIMEOUT, throwable)
                )
                else -> parseError(
                    Failure(RequestsResult.UNKNOWN_ERROR, throwable)
                )
            }
        }
    }

    private fun parseError(failure: Failure) : Either.Error<Failure> = Either.Error(failure)
}