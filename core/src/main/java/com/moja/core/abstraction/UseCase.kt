package com.moja.core.abstraction

import com.moja.core.exception.Failure
import com.moja.core.vo.Either

abstract class UseCase<out Type, in Param> where Type : Any {

    abstract suspend fun run(param: Param) : Either<Failure, Type>

    object None

}