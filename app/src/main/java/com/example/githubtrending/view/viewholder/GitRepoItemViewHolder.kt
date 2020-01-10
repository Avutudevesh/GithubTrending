package com.example.githubtrending.view.viewholder

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
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
    private val startsText: TextView = itemView.findViewById(R.id.stars_text_view)
    private val languageText: TextView = itemView.findViewById(R.id.language_text_view)
    private val forksText: TextView = itemView.findViewById(R.id.forks_text_view)
    private val descriptionText: TextView = itemView.findViewById(R.id.github_repo_description)
    private val expandableContainer: LinearLayout = itemView.findViewById(R.id.expandable_item)

    fun bind(item: GitHubRepoData, isExpandedPosition: Boolean) {
        authorName.text = item.author
        gitHubRepoName.text = item.name
        if (isExpandedPosition) {
            expandableContainer.visibility = View.VISIBLE
            startsText.text = item.stars?.toString() ?: "0"
            languageText.text = item.language.orEmpty()
            forksText.text = item.forks?.toString() ?: "0"
            descriptionText.text = item.description
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.background = itemView.context.getDrawable(R.drawable.list_shadow_background)
            }

        } else {
            expandableContainer.visibility = View.GONE
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,android.R.color.white))
        }
        loadImage(item.avatar)
    }

    private fun loadImage(url: String) {
        Glide.with(avatar.context)
            .load(url)
            .into(avatar)
    }
}