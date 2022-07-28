package br.com.gabrielnovaes.appnews.util.state

sealed class ArticleListEvent {
    object Fetch : ArticleListEvent()
}