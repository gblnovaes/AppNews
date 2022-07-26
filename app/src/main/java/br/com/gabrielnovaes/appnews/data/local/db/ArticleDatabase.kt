package br.com.gabrielnovaes.appnews.data.local.db

import android.content.Context
import androidx.room.*
import br.com.gabrielnovaes.appnews.data.local.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract val getArticleDao: ArticleDao

    companion object {

        @Volatile
        private var instance: ArticleDatabase? = null

        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock) {
            instance ?: createDatabase(context).also { articleDatabase ->
                instance = articleDatabase
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()

    }
}