package com.example.dailyplanet.repository

import com.example.dailyplanet.api.RetrofitInstance
import com.example.dailyplanet.db.ArticleDatabase
import com.example.dailyplanet.models.Article

class NewsRepository(private val db: ArticleDatabase) {

    // --- Network Functions ---

    /**
     * Fetches top headline news from the API for a specific country and category.
     */
    suspend fun getBreakingNews(countryCode: String, category: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, category, pageNumber)

    /**
     * Searches for news from the API based on a search query.
     */
    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    // --- Database Functions ---

    /**
     * Saves an article to the local favorites database.
     */
    suspend fun saveArticle(article: Article) = db.getArticleDao().upsert(article)

    /**
     * Retrieves all saved favorite articles from the database as a Flow.
     */
    fun getSavedNews() = db.getArticleDao().getAllArticles()

    /**
     * Deletes an article from the local favorites database.
     */
    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}