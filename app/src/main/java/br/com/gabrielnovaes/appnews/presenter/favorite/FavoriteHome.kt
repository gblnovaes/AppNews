package br.com.gabrielnovaes.appnews.presenter.favorite

import br.com.gabrielnovaes.appnews.model.Article

interface FavoriteHome {
    interface Presenter {
        fun showArticles(articles : List<Article>)
    }
}