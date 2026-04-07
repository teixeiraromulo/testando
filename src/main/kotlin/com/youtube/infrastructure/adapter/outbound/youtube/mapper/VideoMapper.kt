package com.youtube.infrastructure.adapter.outbound.youtube.mapper

import com.youtube.domain.model.Video
import com.youtube.infrastructure.adapter.outbound.youtube.dto.YouTubeSearchItem

object VideoMapper {

    fun toDomain(item: YouTubeSearchItem): Video {
        return Video(
            id = item.id.videoId.orEmpty(),
            title = item.snippet.title,
            description = item.snippet.description,
            channelTitle = item.snippet.channelTitle,
            publishedAt = item.snippet.publishedAt,
            thumbnailUrl = item.snippet.thumbnails?.medium?.url
                ?: item.snippet.thumbnails?.default?.url
                ?: ""
        )
    }

    fun toDomainList(items: List<YouTubeSearchItem>): List<Video> {
        return items
            .filter { it.id.videoId != null }
            .map { toDomain(it) }
    }
}
