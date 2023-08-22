package com.dev.githubtrending.data.models

data class GithubRepo(
    val id: Long,
    val name: String,
    val url: String,
    val description: String?,
    val language: String?,
    val stars: String,
    val forks: String,
    val owner: String,
    val avatar: String
)