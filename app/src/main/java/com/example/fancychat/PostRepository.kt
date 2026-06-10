package com.example.fancychat

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class PostRepository {

    private val client = SupabaseConfig.client

    /**
     * Lấy tất cả bài viết kèm thông tin Profile và Category
     */
    suspend fun getAllPostsWithDetails(): List<PostItem> {
        return client.postgrest["posts"]
            .select(Columns.raw("*, profiles(*), categories(*)"))
            .decodeList<PostItem>()
    }

    /**
     * Lấy bài viết của tôi kèm thông tin chi tiết
     */
    suspend fun getMyPosts(): List<PostItem> {
        val currentUser = client.auth.currentUserOrNull() 
            ?: throw Exception("Chưa đăng nhập")
            
        return client.postgrest["posts"]
            .select(Columns.raw("*, profiles(*), categories(*)")) {
                filter {
                    eq("author_id", currentUser.id)
                }
            }
            .decodeList<PostItem>()
    }
    
    /**
     * Lấy thông tin profile hiện tại
     */
    suspend fun getCurrentProfile(): Profile? {
        val currentUser = client.auth.currentUserOrNull() ?: return null
        
        return client.postgrest["profiles"]
            .select {
                filter {
                    eq("id", currentUser.id)
                }
            }
            .decodeSingleOrNull<Profile>()
    }
}
