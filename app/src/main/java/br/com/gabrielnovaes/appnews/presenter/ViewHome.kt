package br.com.gabrielnovaes.appnews.presenter

import br.com.gabrielnovaes.appnews.model.Article

interface ViewHome {
    interface View {
        fun showProgressBar()
        fun showFailure(message: String)
        fun hideProgressBar()
        fun showArticles(article: List<Article>)
    }

    interface Favorite {
        fun showArticles(article: List<Article>)
    }
}