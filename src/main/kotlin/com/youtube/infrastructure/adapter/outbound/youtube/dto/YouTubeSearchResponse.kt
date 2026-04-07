package com.youtube.infrastructure.adapter.outbound.youtube.dto

data class YouTubeSearchResponse(
    val items: List<YouTubeSearchItem>
)

data class YouTubeSearchItem(
    val id: YouTubeVideoId,
    val snippet: YouTubeSnippet
)

data class YouTubeVideoId(
    val videoId: String?
)

data class YouTubeSnippet(
    val title: String,
    val description: String,
    val channelTitle: String,
    val publishedAt: String,
    val thumbnails: YouTubeThumbnails?
)

data class YouTubeThumbnails(
    val default: YouTubeThumbnailDetail?,
    val medium: YouTubeThumbnailDetail?,
    val high: YouTubeThumbnailDetail?
)

data class YouTubeThumbnailDetail(
    val url: String
)
