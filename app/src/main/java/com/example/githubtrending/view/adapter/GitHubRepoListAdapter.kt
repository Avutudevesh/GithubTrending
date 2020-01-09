package com.example.githubtrending.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.githubtrending.network.GitHubRepoData
import com.example.githubtrending.view.viewholder.GitRepoItemViewHolder
import javax.inject.Inject

class GitHubRepoListAdapter @Inject constructor() : ListAdapter<GitHubRepoData,GitRepoItemViewHolder>(GitHubRepoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoItemViewHolder {
        return GitRepoItemViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: GitRepoItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

class GitHubRepoDiffCallback : DiffUtil.ItemCallback<GitHubRepoData>() {
    override fun areItemsTheSame(oldItem: GitHubRepoData, newItem: GitHubRepoData): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GitHubRepoData, newItem: GitHubRepoData): Boolean {
        return oldItem == newItem
    }

}