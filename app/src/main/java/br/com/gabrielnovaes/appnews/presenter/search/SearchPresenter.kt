package br.com.gabrielnovaes.appnews.presenter.search

import br.com.gabrielnovaes.appnews.data.local.model.NewsResponse
import br.com.gabrielnovaes.appnews.repository.NewsDataSource
import br.com.gabrielnovaes.appnews.presenter.ViewHome

class SearchPresenter(val view: ViewHome.View,private val dataSource: NewsDataSource) : SearchHome.Presenter {
    override fun search(term: String) {
        this.view.showProgressBar()
        this.dataSource.searchNews(term,this)
    }

    override fun onSuccess(newsResponse: NewsResponse) {
      this.view.showArticles(newsResponse.articles)
    }

    override fun onError(message: String) {
        this.view.showFailure(message)
    }

    override fun onCompleted() {
       this.view.hideProgressBar()
    }
}