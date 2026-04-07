package com.youtube.infrastructure.config

import com.google.gson.Gson
import com.youtube.application.service.SearchVideosService
import com.youtube.domain.port.inbound.SearchVideosUseCase
import com.youtube.domain.port.outbound.VideoRepository
import com.youtube.infrastructure.adapter.outbound.youtube.YouTubeVideoAdapter
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object AppConfig {

    fun createSearchVideosUseCase(apiKey: String): SearchVideosUseCase {
        val videoRepository = createVideoRepository(apiKey)
        return SearchVideosService(videoRepository)
    }

    private fun createVideoRepository(apiKey: String): VideoRepository {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        val gson = Gson()

        return YouTubeVideoAdapter(apiKey, httpClient, gson)
    }
}
