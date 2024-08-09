package com.parsuomash.telescope.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationResponse<T>(
    val data: List<T>,
    val metadata: MetaData
)

@Serializable
data class MetaData(
    @SerialName("current_page") val currentPage: String,
    @SerialName("per_page") val perPage: Int,
    @SerialName("page_count") val pageCount: Int,
    @SerialName("total_count") val totalCount: Int
)
