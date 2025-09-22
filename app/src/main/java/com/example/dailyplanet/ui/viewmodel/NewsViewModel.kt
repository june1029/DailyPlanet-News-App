package com.example.dailyplanet.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplanet.db.ArticleDatabase
import com.example.dailyplanet.models.Article
import com.example.dailyplanet.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class NewsPagingState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val page: Int = 1,
    val endReached: Boolean = false
)

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NewsRepository
    val categories = listOf("General", "Business", "Technology", "Sports", "Health", "Science")

    // State for Home Screen
    private val _breakingNewsStateMap = MutableStateFlow<Map<String, NewsPagingState>>(emptyMap())
    val breakingNewsStateMap: StateFlow<Map<String, NewsPagingState>> = _breakingNewsStateMap

    // State for Search Screen
    private val _searchNewsState = MutableStateFlow(NewsPagingState())
    val searchNewsState: StateFlow<NewsPagingState> = _searchNewsState
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // State for Favorites Screen
    private val _savedNews = MutableStateFlow<List<Article>>(emptyList())
    val savedNews: StateFlow<List<Article>> = _savedNews

    init {
        val articleDatabase = ArticleDatabase(getApplication())
        repository = NewsRepository(articleDatabase)

        repository.getSavedNews().onEach { articles ->
            _savedNews.value = articles
        }.launchIn(viewModelScope)

        getBreakingNewsForCategory(categories.first())
    }

    fun getBreakingNewsForCategory(category: String) {
        val currentState = _breakingNewsStateMap.value[category]
        if (currentState == null || currentState.articles.isEmpty()) {
            viewModelScope.launch {
                _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to NewsPagingState(isLoading = true))
                try {
                    val response = repository.getBreakingNews("us", category.lowercase(), 1)
                    if (response.isSuccessful) {
                        response.body()?.let { newsResponse ->
                            val newState = NewsPagingState(articles = newsResponse.articles, page = 2, endReached = newsResponse.articles.isEmpty())
                            _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to newState)
                        }
                    } else {
                        _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to NewsPagingState(error = "Error: ${response.message()}"))
                    }
                } catch (e: Exception) {
                    _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to NewsPagingState(error = "Check your internet connection."))
                }
            }
        }
    }

    fun getBreakingNewsNextPage(category: String) {
        val currentState = _breakingNewsStateMap.value[category] ?: return
        if (currentState.isLoading || currentState.endReached) return
        viewModelScope.launch {
            _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to currentState.copy(isLoading = true))
            try {
                val response = repository.getBreakingNews("us", category.lowercase(), currentState.page)
                if (response.isSuccessful) {
                    response.body()?.let { newsResponse ->
                        val newArticles = currentState.articles + newsResponse.articles
                        val newState = currentState.copy(articles = newArticles, page = currentState.page + 1, isLoading = false, endReached = newsResponse.articles.isEmpty())
                        _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to newState)
                    }
                } else {
                    _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to currentState.copy(error = "Error: ${response.message()}", isLoading = false))
                }
            } catch (e: Exception) {
                _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to currentState.copy(error = "Check your internet connection.", isLoading = false))
            }
        }
    }

    fun onSearchQueryChange(query: String) { _searchQuery.value = query }
    fun searchNews(query: String) {
        if (query.isBlank()) return
        getSearchNewsNextPage(isNewSearch = true)
    }
    fun getSearchNewsNextPage(isNewSearch: Boolean = false) {
        if (_searchNewsState.value.isLoading || _searchNewsState.value.endReached) return
        viewModelScope.launch {
            _searchNewsState.value = if (isNewSearch) NewsPagingState(isLoading = true) else _searchNewsState.value.copy(isLoading = true)
            val currentPage = if (isNewSearch) 1 else _searchNewsState.value.page
            try {
                val response = repository.searchNews(searchQuery.value, currentPage)
                if (response.isSuccessful) {
                    response.body()?.let { newsResponse ->
                        _searchNewsState.value = _searchNewsState.value.copy(
                            articles = if (isNewSearch) newsResponse.articles else _searchNewsState.value.articles + newsResponse.articles,
                            page = currentPage + 1,
                            isLoading = false,
                            endReached = newsResponse.articles.isEmpty()
                        )
                    }
                } else {
                    _searchNewsState.value = _searchNewsState.value.copy(error = "Error: ${response.message()}", isLoading = false)
                }
            } catch (e: Exception) {
                _searchNewsState.value = _searchNewsState.value.copy(error = "Check your internet connection.", isLoading = false)
            }
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch { repository.saveArticle(article) }
    fun deleteArticle(article: Article) = viewModelScope.launch { repository.deleteArticle(article) }

    fun refreshCategory(category: String) {
        viewModelScope.launch {
            _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to NewsPagingState(isLoading = true))
            try {
                val response = repository.getBreakingNews("us", category.lowercase(), 1)
                if (response.isSuccessful) {
                    response.body()?.let { newsResponse ->
                        val newState = NewsPagingState(articles = newsResponse.articles, page = 2, endReached = newsResponse.articles.isEmpty())
                        _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to newState)
                    }
                } else {
                    _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to NewsPagingState(error = "Error: ${response.message()}"))
                }
            } catch (e: Exception) {
                _breakingNewsStateMap.value = _breakingNewsStateMap.value.plus(category to NewsPagingState(error = "Check your internet connection."))
            }
        }
    }
}