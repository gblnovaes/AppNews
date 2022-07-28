package br.com.gabrielnovaes.appnews.repository

import androidx.lifecycle.LiveData
import br.com.gabrielnovaes.appnews.data.local.model.Article
import br.com.gabrielnovaes.appnews.data.local.db.ArticleDatabase
import br.com.gabrielnovaes.appnews.data.remote.NewsAPI

class NewsRepository(private val api : NewsAPI,private val db : ArticleDatabase) {


    //Remote
    suspend fun getAllRemote()  = api.getBreakingNews()
    suspend fun search(query : String) = api.searchNews(query)

    //Local
    suspend fun updateInsert(article: Article) = db.getArticleDao.updateInsert(article)
    fun getAllArticles() : LiveData<List<Article>> = db.getArticleDao.getAllArticles()
    suspend fun delete(article: Article)  = db.getArticleDao.delete(article)

}