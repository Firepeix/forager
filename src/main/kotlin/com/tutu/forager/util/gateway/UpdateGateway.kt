package com.tutu.forager.util.gateway

interface UpdateGateway<T> {

    suspend fun update(model: T): Result<T>

    suspend fun update(model: T, block: T.() -> T): Result<T> = update(block(model))
}