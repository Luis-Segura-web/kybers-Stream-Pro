package com.kybers.streampro.domain.model

data class Category(
    val categoryId: String,
    val categoryName: String,
    val parentId: String? = null
)