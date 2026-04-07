package com.youtube

import com.youtube.infrastructure.adapter.inbound.console.ConsoleAdapter
import com.youtube.infrastructure.config.AppConfig

fun main(args: Array<String>) {
    val apiKey = System.getenv("YOUTUBE_API_KEY")

    if (apiKey.isNullOrBlank()) {
        println("Erro: A variavel de ambiente YOUTUBE_API_KEY nao esta definida.")
        println("Defina a variavel com: export YOUTUBE_API_KEY=SUA_CHAVE_AQUI")
        return
    }

    val query = if (args.isNotEmpty()) args.joinToString(" ") else "Kotlin tutorial"
    val maxResults = 5

    val searchVideosUseCase = AppConfig.createSearchVideosUseCase(apiKey)
    val consoleAdapter = ConsoleAdapter(searchVideosUseCase)

    consoleAdapter.run(query, maxResults)
}
