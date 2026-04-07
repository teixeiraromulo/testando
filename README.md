# YouTube Data API v3 - Kotlin (Arquitetura Hexagonal)

Aplicacao Kotlin que consome a **YouTube Data API v3** para buscar videos, utilizando **Arquitetura Hexagonal** (Ports & Adapters) e respeitando os principios **SOLID**.

---

## Arquitetura

```
src/main/kotlin/com/youtube/
├── domain/                          # Camada de Dominio
│   ├── model/
│   │   └── Video.kt                # Entidade de dominio
│   └── port/
│       ├── inbound/
│       │   └── SearchVideosUseCase.kt   # Porta de entrada (interface)
│       └── outbound/
│           └── VideoRepository.kt       # Porta de saida (interface)
├── application/                     # Camada de Aplicacao
│   └── service/
│       └── SearchVideosService.kt   # Implementacao do caso de uso
├── infrastructure/                  # Camada de Infraestrutura
│   ├── adapter/
│   │   ├── inbound/
│   │   │   └── console/
│   │   │       └── ConsoleAdapter.kt    # Adaptador de entrada (CLI)
│   │   └── outbound/
│   │       └── youtube/
│   │           ├── YouTubeVideoAdapter.kt   # Adaptador de saida (API)
│   │           ├── dto/
│   │           │   └── YouTubeSearchResponse.kt  # DTOs da API
│   │           └── mapper/
│   │               └── VideoMapper.kt   # Mapeamento DTO -> Dominio
│   └── config/
│       └── AppConfig.kt            # Configuracao e injecao de dependencias
└── Application.kt                   # Ponto de entrada
```

### Principios SOLID Aplicados

| Principio | Aplicacao no Projeto |
|-----------|---------------------|
| **S** - Single Responsibility | Cada classe tem uma unica responsabilidade: `Video` modela dados, `SearchVideosService` orquestra a busca, `YouTubeVideoAdapter` comunica com a API, `VideoMapper` converte DTOs, `ConsoleAdapter` exibe resultados. |
| **O** - Open/Closed | As interfaces `SearchVideosUseCase` e `VideoRepository` permitem estender o comportamento sem modificar o codigo existente. |
| **L** - Liskov Substitution | Qualquer implementacao de `VideoRepository` pode substituir outra sem quebrar o sistema (ex: trocar YouTube por outra API). |
| **I** - Interface Segregation | Interfaces pequenas e focadas: `SearchVideosUseCase` tem apenas o metodo `search`, `VideoRepository` tem apenas `searchByQuery`. |
| **D** - Dependency Inversion | A camada de dominio depende apenas de abstracoes (interfaces). As implementacoes concretas ficam na camada de infraestrutura. |

### Fluxo da Requisicao

```
Console (entrada) -> SearchVideosUseCase (porta) -> SearchVideosService (aplicacao)
    -> VideoRepository (porta) -> YouTubeVideoAdapter (infraestrutura) -> YouTube API
```

---

## Pre-requisitos

- **JDK 17** ou superior
- **Gradle** (o wrapper sera gerado automaticamente)
- Uma **chave de API** do YouTube Data API v3

## Configuracao da API Key

1. Acesse o [Google Cloud Console](https://console.cloud.google.com/)
2. Crie um projeto ou selecione um existente
3. Ative a **YouTube Data API v3** em **APIs e Servicos > Biblioteca**
4. Crie uma **Chave de API** em **APIs e Servicos > Credenciais**
5. Defina a variavel de ambiente:

```bash
export YOUTUBE_API_KEY=SUA_CHAVE_AQUI
```

## Como Executar

```bash
# Compilar o projeto
./gradlew build

# Executar com busca padrao ("Kotlin tutorial")
./gradlew run

# Executar com busca personalizada
./gradlew run --args="seu termo de busca"
```

## Exemplo de Saida

```
Buscando videos para: "Kotlin tutorial"
============================================================

1. Kotlin Course - Tutorial for Beginners
   Canal: freeCodeCamp.org
   Publicado em: 2023-01-15T14:00:00Z
   Link: https://www.youtube.com/watch?v=abc123
   Descricao: Learn Kotlin programming from scratch...

2. Kotlin in 100 Seconds
   Canal: Fireship
   Publicado em: 2023-03-20T16:00:00Z
   Link: https://www.youtube.com/watch?v=def456
   Descricao: Kotlin explained in 100 seconds...

============================================================
Total de videos encontrados: 2
```

## Tecnologias

- **Kotlin** 1.9.22
- **OkHttp** 4.12.0 - Cliente HTTP
- **Gson** 2.10.1 - Serializacao/desserializacao JSON
- **Gradle** com Kotlin DSL - Build tool

## Links Uteis

- [YouTube Data API v3 - Documentacao](https://developers.google.com/youtube/v3?hl=pt-br)
- [Guia de Inicio Rapido](https://developers.google.com/youtube/v3/getting-started?hl=pt-br)
- [Referencia da API - Search](https://developers.google.com/youtube/v3/docs/search/list?hl=pt-br)
- [Google Cloud Console](https://console.cloud.google.com/)

## Licenca

Este projeto esta licenciado sob a licenca MIT.
