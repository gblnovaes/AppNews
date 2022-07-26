package br.com.gabrielnovaes.appnews.presenter.news

import br.com.gabrielnovaes.appnews.data.local.model.NewsResponse
import br.com.gabrielnovaes.appnews.repository.NewsDataSource
import br.com.gabrielnovaes.appnews.presenter.ViewHome

class NewsPresenter(private val news: ViewHome.View, private val dataSource: NewsDataSource) :
    NewsHome.Presenter {
    override fun requestAll() {
        this.news.showProgressBar()
        this.dataSource.getBreakingNews(this)
    }

    override fun onSuccess(newsResponse: NewsResponse) {
        this.news.showArticles(newsResponse.articles)
    }

    override fun onError(message: String) {
        this.news.showFailure(message)
    }

    override fun onCompleted() {
        this.news.hideProgressBar()
    }
}