package com.example.githubtrending.view.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubtrending.R
import com.example.githubtrending.network.GitHubRepoData
import de.hdodenhof.circleimageview.CircleImageView

class GitRepoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun from(parent: ViewGroup): GitRepoItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.git_repo_item, parent, false)
            return GitRepoItemViewHolder(view)
        }
    }

    private val authorName: TextView = itemView.findViewById(R.id.author_name)
    private val gitHubRepoName: TextView = itemView.findViewById(R.id.repo_name)
    private val avatar: CircleImageView = itemView.findViewById(R.id.avatar)

    fun bind(item: GitHubRepoData) {
        authorName.text = item.author
        gitHubRepoName.text = item.name
        loadImage(item.avatar)
    }

    private fun loadImage(url: String) {
        Glide.with(avatar.context)
            .load(url)
            .into(avatar)
    }
}