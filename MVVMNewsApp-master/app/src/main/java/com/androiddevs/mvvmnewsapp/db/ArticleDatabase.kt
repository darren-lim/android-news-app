package com.androiddevs.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.mvvmnewsapp.models.Article

// database classes for room will always be abstract
@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object {
        // other threads can see when instance is changed
        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK = Any() // only single instance of db

        // called whenever db is instantiated
        operator fun invoke(context:Context) = instance ?: synchronized(LOCK) {
            // cannot be accessed by other threads at the same time
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}