package com.example.fancychat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import coil.load
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.imgMyProfile).setOnClickListener {
            findNavController().navigate(R.id.SettingsFragment)
        }

        view.findViewById<View>(R.id.fabCreatePost).setOnClickListener {
            Toast.makeText(context, "Mở trình soạn thảo bài viết mới!", Toast.LENGTH_SHORT).show()
        }

        val rvCategories = view.findViewById<RecyclerView>(R.id.rvCategories)
        val rvTrending = view.findViewById<RecyclerView>(R.id.rvTrending)
        val rvPosts = view.findViewById<RecyclerView>(R.id.rvPosts)

        setupCategories(rvCategories)
        
        // Khởi tạo adapter rỗng trước
        val trendingAdapter = PostAdapter(emptyList(), isTrending = true)
        val postsAdapter = PostAdapter(emptyList())
        
        rvTrending.adapter = trendingAdapter
        rvPosts.adapter = postsAdapter

        // Fetch dữ liệu từ Supabase
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Trong thực tế, bạn có thể dùng select() kèm join để lấy tên tác giả
                // Ở đây ta lấy đơn giản từ bảng posts
                val fetchedPosts = SupabaseConfig.client.postgrest["posts"]
                    .select()
                    .decodeList<PostItem>()
                
                if (fetchedPosts.isNotEmpty()) {
                    trendingAdapter.updateData(fetchedPosts.take(3))
                    postsAdapter.updateData(fetchedPosts)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Lỗi kết nối CSDL: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupCategories(recyclerView: RecyclerView) {
        val categories = listOf("Tất cả", "Nghệ thuật", "Xu hướng", "Thiết kế", "Đời sống", "Công nghệ", "Âm nhạc", "Phim ảnh")
        recyclerView.adapter = CategoryAdapter(categories)
    }

    private class CategoryAdapter(private val items: List<String>) :
        RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(R.id.tvCategoryName)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = 
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false))
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvName.text = items[position]
        }
        override fun getItemCount() = items.size
    }

    private class PostAdapter(private var items: List<PostItem>, private val isTrending: Boolean = false) :
        RecyclerView.Adapter<PostAdapter.ViewHolder>() {
        
        fun updateData(newItems: List<PostItem>) {
            items = newItems
            notifyDataSetChanged()
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTitle: TextView = view.findViewById(R.id.tvPostTitle)
            val tvAuthor: TextView = view.findViewById(R.id.tvAuthorName)
            val tvCategory: TextView = view.findViewById(R.id.tvCategory)
            val tvLikes: TextView = view.findViewById(R.id.tvLikes)
            val imgPost: android.widget.ImageView = view.findViewById(R.id.imgPost)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_staggered, parent, false)
            if (isTrending) {
                view.layoutParams.width = (parent.context.resources.displayMetrics.widthPixels * 0.7).toInt()
            }
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.tvTitle.text = item.title
            holder.tvAuthor.text = item.profiles?.fullName ?: "Unknown"
            holder.tvCategory.text = item.categories?.name ?: "No Category"
            holder.tvLikes.text = "❤ ${item.likes}"
            
            // Load ảnh từ URL bằng Coil
            item.imageUrl?.let { url ->
                holder.imgPost.load(url) {
                    crossfade(true)
                    placeholder(R.drawable.bg_circle_pink)
                    error(R.drawable.bg_circle_pink)
                }
            } ?: run {
                holder.imgPost.setImageResource(R.drawable.bg_circle_pink)
            }
        }
        override fun getItemCount() = items.size
    }
}
