package com.kybers.streampro.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.kybers.streampro.domain.model.Category

@JsonClass(generateAdapter = true)
data class CategoryDto(
    @Json(name = "category_id") val categoryId: String?,
    @Json(name = "category_name") val categoryName: String?,
    @Json(name = "parent_id") val parentId: String?
) {
    fun toDomain() = Category(
        categoryId = categoryId ?: "",
        categoryName = categoryName ?: "",
        parentId = parentId
    )
}