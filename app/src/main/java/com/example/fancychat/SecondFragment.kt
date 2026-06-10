package com.example.fancychat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvStories = view.findViewById<RecyclerView>(R.id.rvStories)
        val rvChats = view.findViewById<RecyclerView>(R.id.rvChats)

        setupStories(rvStories)
        setupChats(rvChats)
    }

    private fun setupStories(recyclerView: RecyclerView) {
        val stories = listOf("Alex", "Bender", "John", "Sarah", "Mike")
        recyclerView.adapter = StoryAdapter(stories)
    }

    private fun setupChats(recyclerView: RecyclerView) {
        val chats = listOf("Nhóm thiết kế", "Đội phát triển", "Gia đình", "Bạn bè", "Công việc")
        recyclerView.adapter = ChatAdapter(chats)
    }

    // Adapter cho Stories
    private class StoryAdapter(private val stories: List<String>) :
        RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

        class StoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(R.id.tvStoryName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
            return StoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
            holder.tvName.text = stories[position]
        }

        override fun getItemCount() = stories.size
    }

    // Adapter cho Chats
    private class ChatAdapter(private val chats: List<String>) :
        RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

        class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(R.id.tvChatName)
            val tvMessage: TextView = view.findViewById(R.id.tvChatMessage)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
            return ChatViewHolder(view)
        }

        override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
            holder.tvName.text = chats[position]
            holder.tvMessage.text = "Tin nhắn mới từ ${chats[position]}"
        }

        override fun getItemCount() = chats.size
    }
}
