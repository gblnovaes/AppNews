package br.com.gabrielnovaes.appnews.repository

import br.com.gabrielnovaes.appnews.data.local.model.Article
import br.com.gabrielnovaes.appnews.data.local.db.ArticleDatabase

class NewsRepository(private val db : ArticleDatabase) {

    suspend fun updateInsert(article: Article) = db.getArticleDao.updateInsert(article)

    fun getAllArticles() : List<Article> = db.getArticleDao.getAllArticles()

    suspend fun delete(article: Article)  = db.getArticleDao.delete(article)

}