package com.youtube.infrastructure.adapter.outbound.youtube

import com.google.gson.Gson
import com.youtube.domain.model.Video
import com.youtube.domain.port.outbound.VideoRepository
import com.youtube.infrastructure.adapter.outbound.youtube.dto.YouTubeSearchResponse
import com.youtube.infrastructure.adapter.outbound.youtube.mapper.VideoMapper
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class YouTubeVideoAdapter(
    private val apiKey: String,
    private val httpClient: OkHttpClient,
    private val gson: Gson
) : VideoRepository {

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/youtube/v3/search"
    }

    override fun searchByQuery(query: String, maxResults: Int): List<Video> {
        val url = BASE_URL.toHttpUrl().newBuilder()
            .addQueryParameter("part", "snippet")
            .addQueryParameter("q", query)
            .addQueryParameter("maxResults", maxResults.toString())
            .addQueryParameter("type", "video")
            .addQueryParameter("key", apiKey)
            .build()

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = httpClient.newCall(request).execute()

        if (!response.isSuccessful) {
            throw IOException("Erro na requisicao a API do YouTube: ${response.code} - ${response.message}")
        }

        val responseBody = response.body?.string()
            ?: throw IOException("Resposta vazia da API do YouTube")

        val searchResponse = gson.fromJson(responseBody, YouTubeSearchResponse::class.java)

        return VideoMapper.toDomainList(searchResponse.items)
    }
}
