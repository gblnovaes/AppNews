package br.com.gabrielnovaes.appnews.util.state

import br.com.gabrielnovaes.appnews.data.local.model.Article

sealed class ArticleListState {
    data class Success(val list: List<Article>) : ArticleListState()
    data class ErrorMessage(val message: String) : ArticleListState()
    object Loading : ArticleListState()
    object Empty : ArticleListState()
}