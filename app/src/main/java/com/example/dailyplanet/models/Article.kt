package com.example.dailyplanet.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "articles"
)
data class Article(
    // We make the URL the primary key because it's unique for each article.
    @PrimaryKey
    val url: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val urlToImage: String?
)