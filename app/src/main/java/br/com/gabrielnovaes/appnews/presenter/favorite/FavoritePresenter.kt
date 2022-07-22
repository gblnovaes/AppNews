package br.com.gabrielnovaes.appnews.presenter.favorite

import br.com.gabrielnovaes.appnews.model.Article
import br.com.gabrielnovaes.appnews.model.data.NewsDataSource

class FavoritePresenter(favoriteHome: FavoriteHome,private val dataSource: NewsDataSource) : FavoriteHome.Presenter {
    override fun showArticles(articles: List<Article>) {

    }
}