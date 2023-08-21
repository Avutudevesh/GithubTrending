package com.dev.githubtrending.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object Constants {
    const val GITHUB_TRENDING_BASE_URL = "https://api.github.com/"

    @OptIn(ExperimentalSerializationApi::class)
    val JSON = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        explicitNulls = false
        isLenient = true
    }
}