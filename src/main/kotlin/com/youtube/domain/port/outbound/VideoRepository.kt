package com.youtube.domain.port.outbound

import com.youtube.domain.model.Video

interface VideoRepository {
    fun searchByQuery(query: String, maxResults: Int): List<Video>
}
