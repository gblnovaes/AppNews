package br.com.gabrielnovaes.appnews.data.local.model

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)