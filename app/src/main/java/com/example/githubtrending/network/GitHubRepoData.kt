package com.example.githubtrending.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubRepoData(
    val author: String,
    val name: String,
    val avatar: String,
    val description: String
) : Parcelable