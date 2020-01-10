package com.example.githubtrending.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.githubtrending.network.GitHubRepoData
import com.example.githubtrending.view.viewholder.GitRepoItemViewHolder
import javax.inject.Inject

class GitHubRepoListAdapter @Inject constructor() :
    ListAdapter<GitHubRepoData, GitRepoItemViewHolder>(GitHubRepoDiffCallback()) {

    private var expandedPosition = -1
    private var previousExpandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoItemViewHolder {
        return GitRepoItemViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: GitRepoItemViewHolder, position: Int) {
        val item = getItem(position)
        val isExpanded = position == expandedPosition
        holder.itemView.isActivated = isExpanded
        if (isExpanded)
            previousExpandedPosition = position
        holder.bind(item, isExpanded)
        holder.itemView.setOnClickListener {
            expandedPosition = if (isExpanded) {
                -1
            } else {
                position
            }
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(expandedPosition)
        }
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