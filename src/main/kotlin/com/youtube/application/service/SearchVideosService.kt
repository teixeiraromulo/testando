package com.youtube.application.service

import com.youtube.domain.model.Video
import com.youtube.domain.port.inbound.SearchVideosUseCase
import com.youtube.domain.port.outbound.VideoRepository

class SearchVideosService(
    private val videoRepository: VideoRepository
) : SearchVideosUseCase {

    override fun search(query: String, maxResults: Int): List<Video> {
        require(query.isNotBlank()) { "A query de busca nao pode ser vazia" }
        require(maxResults in 1..50) { "maxResults deve ser entre 1 e 50" }

        return videoRepository.searchByQuery(query, maxResults)
    }
}
