package com.example.fancychat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostItem(
    val id: String? = null,
    val title: String,
    val content: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("category_id") val categoryId: Long? = null,
    @SerialName("author_id") val authorId: String? = null,
    @SerialName("likes_count") val likes: Int = 0,
    @SerialName("created_at") val createdAt: String? = null,
    
    // Đối tượng Join từ Supabase
    val profiles: Profile? = null,
    val categories: Category? = null
)
