package br.com.gabrielnovaes.appnews.model.data

import android.content.Context
import br.com.gabrielnovaes.appnews.model.Article
import br.com.gabrielnovaes.appnews.model.db.ArticleDatabase
import br.com.gabrielnovaes.appnews.network.RetrofitInstance
import br.com.gabrielnovaes.appnews.presenter.favorite.FavoriteHome
import br.com.gabrielnovaes.appnews.presenter.news.NewsHome
import br.com.gabrielnovaes.appnews.presenter.search.SearchHome
import kotlinx.coroutines.*

class NewsDataSource(context: Context) {

    private var db = ArticleDatabase(context)
    private var newsRepository: NewsRepository = NewsRepository(db)

    fun getBreakingNews(callback: NewsHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getBreakingNews("br")
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onCompleted()
            } else {
                callback.onError(response.message())
                callback.onCompleted()
            }
        }
    }

    fun searchNews(term: String, callback: SearchHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.searchNews(term)
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onCompleted()
            } else {
                callback.onError(response.message())
                callback.onCompleted()
            }
        }
    }

    fun saveArticle(article: Article) {
        GlobalScope.launch(Dispatchers.Main) {
            newsRepository.updateInsert(article)
        }
    }

    fun getAllArticles(callback: FavoriteHome.Presenter) {
        var allArticles: List<Article>
        CoroutineScope(Dispatchers.IO).launch {
            allArticles = newsRepository.getAllArticles()

            withContext(Dispatchers.Main) {
                callback.onSuccess(allArticles)
            }
        }
    }

    fun deleteArticle(article: Article?) {
        GlobalScope.launch(Dispatchers.Main) {
            article?.let { article ->
                newsRepository.delete(article)
            }
        }
    }
}