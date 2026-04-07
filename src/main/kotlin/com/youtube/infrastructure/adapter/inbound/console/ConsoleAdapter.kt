package com.youtube.infrastructure.adapter.inbound.console

import com.youtube.domain.model.Video
import com.youtube.domain.port.inbound.SearchVideosUseCase

class ConsoleAdapter(
    private val searchVideosUseCase: SearchVideosUseCase
) {

    fun run(query: String, maxResults: Int) {
        println("Buscando videos para: \"$query\"")
        println("=" .repeat(60))

        try {
            val videos = searchVideosUseCase.search(query, maxResults)

            if (videos.isEmpty()) {
                println("Nenhum video encontrado.")
                return
            }

            videos.forEachIndexed { index, video ->
                printVideo(index + 1, video)
            }

            println("=" .repeat(60))
            println("Total de videos encontrados: ${videos.size}")
        } catch (e: IllegalArgumentException) {
            println("Erro de validacao: ${e.message}")
        } catch (e: Exception) {
            println("Erro ao buscar videos: ${e.message}")
        }
    }

    private fun printVideo(index: Int, video: Video) {
        println()
        println("$index. ${video.title}")
        println("   Canal: ${video.channelTitle}")
        println("   Publicado em: ${video.publishedAt}")
        println("   Link: https://www.youtube.com/watch?v=${video.id}")
        println("   Descricao: ${video.description.take(100)}...")
    }
}
