package com.moja.core.exception

data class Failure(
    val requestsResult: Int,
    val throwable: Throwable
)