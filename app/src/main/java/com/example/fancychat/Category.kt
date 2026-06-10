package com.example.fancychat

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Long? = null,
    val name: String,
    val slug: String
)
