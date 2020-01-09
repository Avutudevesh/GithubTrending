package com.example.githubtrending.view.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrending.R
import com.example.githubtrending.network.GitHubRepoData

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

    fun bind(item: GitHubRepoData) {
        authorName.text = item.author
        gitHubRepoName.text = item.name
    }
}