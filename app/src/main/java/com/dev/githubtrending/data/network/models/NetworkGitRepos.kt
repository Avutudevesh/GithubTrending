package com.dev.githubtrending.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NetworkGitRepos(
    val items: List<Repo>
)

@Serializable
data class Repo(
    val id: Long,
    val name: String,
    @SerialName("html_url")
    val url: String,
    val description: String?,
    val language: String?,
    @SerialName("stargazers_count")
    val stars: String,
    @SerialName("forks_count")
    val forks: String,
    val owner: Owner
)

@Serializable
data class Owner(
    val login: String,
    @SerialName("avatar_url")
    val avatar: String
)