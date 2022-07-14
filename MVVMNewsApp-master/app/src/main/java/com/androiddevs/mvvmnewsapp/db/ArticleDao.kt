package com.androiddevs.mvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.androiddevs.mvvmnewsapp.models.Article

// data access object
@Dao
interface ArticleDao {

    // onconflict defines what happens when there exists already
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    // returns live data obj, does not work with suspend
    // live data is class of android components. Enables fragments to subscribe to live data.
    // if live data changes, it notifies all subscribers to update data.
    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}