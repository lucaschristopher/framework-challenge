package com.example.desafioframework.core

import com.example.desafioframework.data.model.ToDo
import kotlinx.coroutines.flow.Flow

/**
 * Generic Use Case with Parameters and Outputs
 */
abstract class UseCase<Param, Source> {

    abstract suspend fun execute(param: Param): Flow<Source>

    open suspend operator fun invoke(param: Param) = execute(param)

    // When we don't need to pass any parameters, we'll just receive outputs
    abstract class NoParam<Source> : UseCase<None, Flow<Source>>() {
        abstract suspend fun execute(): List<*>

        final override suspend fun execute(param: None) =
            throw UnsupportedOperationException()

        suspend operator fun invoke(): List<*> = execute()
    }

    // When we just pass the parameters without being interested in some output value
    abstract class NoSource<Params> : UseCase<Params, Unit>() {
        override suspend operator fun invoke(param: Params) = execute(param)
    }

    object None
}