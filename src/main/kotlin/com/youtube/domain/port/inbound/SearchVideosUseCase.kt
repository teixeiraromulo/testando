package com.youtube.domain.port.inbound

import com.youtube.domain.model.Video

interface SearchVideosUseCase {
    fun search(query: String, maxResults: Int): List<Video>
}
