package br.com.gabrielnovaes.appnews.model.data

import br.com.gabrielnovaes.appnews.network.RetrofitInstance
import br.com.gabrielnovaes.appnews.presenter.news.NewsHome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsDataSource{

    fun getBreakingNews(callback : NewsHome.Presenter){
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getBreakingNews("br")
            if(response.isSuccessful){
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onCompleted()
            }else{
                callback.onError(response.message())
                callback.onCompleted()
            }
        }
    }
}