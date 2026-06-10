package com.example.fancychat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fancychat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupStories()
        setupChats()
    }

    private fun setupStories() {
        val stories = listOf("Alex", "Bender", "John", "Sarah", "Mike")
        binding.rvStories.adapter = StoryAdapter(stories)
    }

    private fun setupChats() {
        val chats = listOf("Design team", "Development", "Family", "Friends", "Work")
        binding.rvChats.adapter = ChatAdapter(chats)
    }

    // Adapter for Stories
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

    // Adapter for Chats
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
            holder.tvMessage.text = "Awesome message for ${chats[position]}"
        }

        override fun getItemCount() = chats.size
    }
}
