package br.com.gabrielnovaes.appnews.presenter.favorite

import br.com.gabrielnovaes.appnews.model.Article
import br.com.gabrielnovaes.appnews.model.data.NewsDataSource
import br.com.gabrielnovaes.appnews.presenter.ViewHome

class FavoritePresenter(val view: ViewHome.Favorite,private val dataSource: NewsDataSource) : FavoriteHome.Presenter {

    fun saveArticle(article: Article){
        dataSource.saveArticle(article)
    }

    override fun onSuccess(articles: List<Article>) {
        this.view.showArticles(articles)
    }

    fun getAll() {
        dataSource.getAllArticles(this)
    }

    fun deleteArticle(article: Article?) {
        dataSource.deleteArticle(article)
    }
}