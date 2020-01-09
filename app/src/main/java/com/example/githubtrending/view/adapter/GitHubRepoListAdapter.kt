package com.example.githubtrending.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrending.R
import com.example.githubtrending.network.GitHubRepoData
import com.example.githubtrending.view.viewholder.TextItemViewHolder
import javax.inject.Inject

class GitHubRepoListAdapter @Inject constructor() : RecyclerView.Adapter<TextItemViewHolder>() {

    var data = listOf<GitHubRepoData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.git_repo_item, parent, false) as TextView
        return TextItemViewHolder(view)

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.author
    }

}