package br.com.gabrielnovaes.appnews.presenter.favorite

import br.com.gabrielnovaes.appnews.data.local.model.Article

interface FavoriteHome {
    interface Presenter {
        fun onSuccess(articles : List<Article>)
    }
}