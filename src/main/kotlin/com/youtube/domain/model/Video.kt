package com.youtube.domain.model

data class Video(
    val id: String,
    val title: String,
    val description: String,
    val channelTitle: String,
    val publishedAt: String,
    val thumbnailUrl: String
)
