package br.com.gabrielnovaes.appnews.presenter.search

import br.com.gabrielnovaes.appnews.model.NewsResponse
import br.com.gabrielnovaes.appnews.model.data.NewsDataSource
import br.com.gabrielnovaes.appnews.presenter.ViewHome

class SearchPresenter(val viewHome: ViewHome.View,private val dataSource: NewsDataSource) : SearchHome.Presenter {
    override fun search(term: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(newsResponse: NewsResponse) {
        TODO("Not yet implemented")
    }

    override fun onError(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCompleted() {
        TODO("Not yet implemented")
    }
}