package com.bravedeveloper.domain.model.city.order.search

data class PaginationInfo(
    val totalPages: Int,
    val totalItems: Int,
    val page: Int?,
    val perPage: Int?,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean
)
