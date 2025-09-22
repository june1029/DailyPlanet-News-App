package com.example.dailyplanet.db

import androidx.room.*
import com.example.dailyplanet.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    // Insert or update an article. If it already exists, it will be replaced.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    // Get all saved articles, wrapped in a Flow so the UI automatically updates on changes.
    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Article>>

    // Delete an article.
    @Delete
    suspend fun deleteArticle(article: Article)
}