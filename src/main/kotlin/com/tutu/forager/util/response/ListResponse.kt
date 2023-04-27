package com.tutu.forager.util.response

import kotlinx.serialization.Serializable

@Serializable
data class ListResponse<T>(val data: List<T>) {

    @Serializable
    data class Metadata(val count: Int)

    val metadata = Metadata(data.size)
}