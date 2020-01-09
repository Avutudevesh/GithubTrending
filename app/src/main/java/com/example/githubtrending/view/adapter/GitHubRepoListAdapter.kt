package com.example.githubtrending.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrending.network.GitHubRepoData
import com.example.githubtrending.view.viewholder.GitRepoItemViewHolder
import javax.inject.Inject

class GitHubRepoListAdapter @Inject constructor() : RecyclerView.Adapter<GitRepoItemViewHolder>() {

    var data = listOf<GitHubRepoData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoItemViewHolder {
        return GitRepoItemViewHolder.from(parent)

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: GitRepoItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

}