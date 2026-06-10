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
        val rvPosts = view.findViewById<RecyclerView>(R.id.rvPosts)

        setupCategories(rvCategories)
        setupPosts(rvPosts)
    }

    private fun setupCategories(recyclerView: RecyclerView) {
        val categories = listOf("Tất cả", "Nghệ thuật", "Xu hướng", "Thiết kế", "Đời sống", "Công nghệ")
        recyclerView.adapter = CategoryAdapter(categories)
    }

    private fun setupPosts(recyclerView: RecyclerView) {
        val posts = listOf(
            PostItem("Nghệ thuật 3D sáng tạo", "Alex bender"),
            PostItem("Xu hướng thiết kế UI/UX mới nhất năm 2024", "Design Team"),
            PostItem("Chuyến du lịch khám phá Sa Pa mùa lúa chín", "Minh Tú"),
            PostItem("Kỹ năng lập trình Android nâng cao cho người mới", "Coder VN"),
            PostItem("Món ngon mỗi ngày: Bí quyết làm salad", "Chef Hùng"),
            PostItem("Kiến trúc nội thất tối giản phong cách Bắc Âu", "Studio Home")
        )
        recyclerView.adapter = PostAdapter(posts)
    }

    data class PostItem(val title: String, val author: String)

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

    private class PostAdapter(private val items: List<PostItem>) :
        RecyclerView.Adapter<PostAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTitle: TextView = view.findViewById(R.id.tvPostTitle)
            val tvAuthor: TextView = view.findViewById(R.id.tvAuthorName)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = 
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post_staggered, parent, false))
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvTitle.text = items[position].title
            holder.tvAuthor.text = items[position].author
        }
        override fun getItemCount() = items.size
    }
}
