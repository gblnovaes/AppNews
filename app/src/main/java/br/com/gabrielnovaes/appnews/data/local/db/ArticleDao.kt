package br.com.gabrielnovaes.appnews.data.local.db

import androidx.room.*
import br.com.gabrielnovaes.appnews.data.local.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(article: Article): Long

    @Query("SELECT * from articles")
    fun getAllArticles(): List<Article>

    @Delete
    suspend fun delete(article: Article)
}