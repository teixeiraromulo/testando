# Projeto com YouTube Data API v3

Guia completo para iniciar um projeto utilizando a **YouTube Data API v3** do Google.

---

## Resumo da API do YouTube

A API do YouTube oferece diversas funcionalidades que permitem integrar recursos do YouTube em aplicacoes proprias. As principais APIs disponiveis sao:

| API | Descricao |
|-----|-----------|
| **YouTube Data API (v3)** | Permite buscar videos, gerenciar playlists, enviar videos, gerenciar inscricoes e configuracoes de canal. |
| **YouTube Analytics API** | Recupera estatisticas de visualizacao, metricas de popularidade e dados de desempenho de videos e canais. |
| **YouTube Live Streaming API** | Agenda transmissoes ao vivo e gerencia streams de video em tempo real. |
| **IFrame Player API** | Incorpora um player do YouTube em aplicacoes web. |

### Recursos Principais da Data API v3

A Data API v3 permite interagir com os seguintes recursos:

- **video** - Representacao de um video do YouTube
- **channel** - Informacoes sobre um canal
- **playlist** - Colecao de videos organizados sequencialmente
- **playlistItem** - Video individual dentro de uma playlist
- **search result** - Resultados de pesquisa por videos, canais ou playlists
- **comment / commentThread** - Comentarios e threads de comentarios
- **subscription** - Inscricoes de usuarios em canais
- **activity** - Acoes realizadas por um usuario no YouTube
- **thumbnail** - Imagens de miniatura de recursos
- **videoCategory** - Categorias de videos

### Operacoes Suportadas

| Operacao | Metodo HTTP | Descricao |
|----------|-------------|-----------|
| `list` | GET | Recupera uma lista de recursos |
| `insert` | POST | Cria um novo recurso |
| `update` | PUT | Atualiza um recurso existente |
| `delete` | DELETE | Remove um recurso especifico |

### Sistema de Cotas

- Cota padrao: **10.000 unidades por dia**
- Leitura (list): **1 unidade**
- Escrita (insert/update/delete): **50 unidades**
- Pesquisa (search): **100 unidades**
- Upload de video: **100 unidades**

---

## Primeiros Passos

### 1. Pre-requisitos

- [Node.js](https://nodejs.org/) v18 ou superior
- Uma conta Google
- Acesso ao [Google Cloud Console](https://console.cloud.google.com/)

### 2. Configurar o Projeto no Google Cloud

1. Acesse o [Google Cloud Console](https://console.cloud.google.com/)
2. Crie um novo projeto ou selecione um existente
3. No menu lateral, va em **APIs e Servicos > Biblioteca**
4. Pesquise por **YouTube Data API v3** e clique em **Ativar**
5. Va em **APIs e Servicos > Credenciais**
6. Clique em **Criar Credenciais** e escolha:
   - **Chave de API** - para acessos publicos (leitura de dados publicos)
   - **ID do cliente OAuth 2.0** - para acessos que requerem autorizacao do usuario (upload, gerenciamento de playlists, etc.)

### 3. Inicializar o Projeto

```bash
# Criar o diretorio do projeto
mkdir meu-projeto-youtube
cd meu-projeto-youtube

# Inicializar o projeto Node.js
npm init -y

# Instalar a biblioteca do Google APIs
npm install googleapis
```

### 4. Configurar Variaveis de Ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
YOUTUBE_API_KEY=SUA_CHAVE_DE_API_AQUI
```

Instale o dotenv para carregar as variaveis:

```bash
npm install dotenv
```

> **Importante:** Adicione `.env` ao seu `.gitignore` para nao expor suas credenciais.

### 5. Exemplo: Buscar Videos

Crie o arquivo `index.js`:

```javascript
require('dotenv').config();
const { google } = require('googleapis');

const youtube = google.youtube({
  version: 'v3',
  auth: process.env.YOUTUBE_API_KEY,
});

async function buscarVideos(termo) {
  try {
    const resposta = await youtube.search.list({
      part: 'snippet',
      q: termo,
      maxResults: 5,
      type: 'video',
    });

    const videos = resposta.data.items;

    console.log(`\nResultados para: "${termo}"\n`);
    videos.forEach((video, index) => {
      console.log(`${index + 1}. ${video.snippet.title}`);
      console.log(`   Canal: ${video.snippet.channelTitle}`);
      console.log(`   Link: https://www.youtube.com/watch?v=${video.id.videoId}`);
      console.log(`   Publicado em: ${video.snippet.publishedAt}\n`);
    });
  } catch (erro) {
    console.error('Erro ao buscar videos:', erro.message);
  }
}

// Executar a busca
buscarVideos('nodejs tutorial');
```

Execute o projeto:

```bash
node index.js
```

### 6. Exemplo: Obter Detalhes de um Video

```javascript
async function detalhesVideo(videoId) {
  try {
    const resposta = await youtube.videos.list({
      part: 'snippet,statistics,contentDetails',
      id: videoId,
    });

    const video = resposta.data.items[0];

    if (video) {
      console.log(`Titulo: ${video.snippet.title}`);
      console.log(`Canal: ${video.snippet.channelTitle}`);
      console.log(`Visualizacoes: ${video.statistics.viewCount}`);
      console.log(`Likes: ${video.statistics.likeCount}`);
      console.log(`Duracao: ${video.contentDetails.duration}`);
    }
  } catch (erro) {
    console.error('Erro ao buscar detalhes:', erro.message);
  }
}
```

### 7. Exemplo: Listar Playlists de um Canal

```javascript
async function listarPlaylists(channelId) {
  try {
    const resposta = await youtube.playlists.list({
      part: 'snippet,contentDetails',
      channelId: channelId,
      maxResults: 10,
    });

    const playlists = resposta.data.items;

    console.log(`\nPlaylists do canal:\n`);
    playlists.forEach((playlist, index) => {
      console.log(`${index + 1}. ${playlist.snippet.title}`);
      console.log(`   Videos: ${playlist.contentDetails.itemCount}`);
      console.log(`   ID: ${playlist.id}\n`);
    });
  } catch (erro) {
    console.error('Erro ao listar playlists:', erro.message);
  }
}
```

---

## Estrutura Sugerida do Projeto

```
meu-projeto-youtube/
├── .env                  # Variaveis de ambiente (nao commitar!)
├── .gitignore
├── package.json
├── index.js              # Ponto de entrada
├── src/
│   ├── config/
│   │   └── youtube.js    # Configuracao do cliente YouTube
│   ├── services/
│   │   ├── search.js     # Servico de busca
│   │   ├── video.js      # Servico de videos
│   │   └── playlist.js   # Servico de playlists
│   └── utils/
│       └── formatter.js  # Formatacao de dados
└── README.md
```

---

## Autenticacao OAuth 2.0 (para operacoes com autorizacao)

Para operacoes que requerem autorizacao do usuario (upload de videos, gerenciamento de playlists privadas, etc.), e necessario configurar OAuth 2.0:

1. No Google Cloud Console, crie credenciais do tipo **ID do cliente OAuth 2.0**
2. Configure a tela de consentimento OAuth
3. Baixe o arquivo `client_secret.json`

```javascript
const { google } = require('googleapis');
const readline = require('readline');

const oauth2Client = new google.auth.OAuth2(
  'SEU_CLIENT_ID',
  'SEU_CLIENT_SECRET',
  'http://localhost:3000/callback'
);

// Gerar URL de autorizacao
const authUrl = oauth2Client.generateAuthUrl({
  access_type: 'offline',
  scope: ['https://www.googleapis.com/auth/youtube'],
});

console.log('Autorize o acesso visitando:', authUrl);
```

---

## Links Uteis

- [Documentacao Oficial da YouTube Data API v3](https://developers.google.com/youtube/v3?hl=pt-br)
- [Guia de Inicio Rapido](https://developers.google.com/youtube/v3/getting-started?hl=pt-br)
- [APIs Explorer (para testar requisicoes)](https://developers.google.com/youtube/v3/docs?hl=pt-br)
- [Calculadora de Cotas](https://developers.google.com/youtube/v3/determine_quota_cost?hl=pt-br)
- [Exemplos de Codigo Oficiais](https://developers.google.com/youtube/v3/code_samples?hl=pt-br)
- [Google Cloud Console](https://console.cloud.google.com/)

---

## Licenca

Este projeto esta licenciado sob a licenca MIT.
