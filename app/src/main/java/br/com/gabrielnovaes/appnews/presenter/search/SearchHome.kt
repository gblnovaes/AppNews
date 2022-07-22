package br.com.gabrielnovaes.appnews.presenter.search

import br.com.gabrielnovaes.appnews.model.NewsResponse

interface SearchHome {
    interface Presenter{
        fun search(term : String)
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message : String)
        fun onCompleted()
    }
}