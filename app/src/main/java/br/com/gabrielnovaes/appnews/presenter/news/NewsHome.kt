package br.com.gabrielnovaes.appnews.presenter.news

import br.com.gabrielnovaes.appnews.model.NewsResponse

class NewsHome {
    interface Presenter{
        fun requestAll()
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message : String)
        fun onCompleted()
    }
}