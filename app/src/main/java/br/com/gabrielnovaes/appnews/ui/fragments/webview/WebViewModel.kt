package br.com.gabrielnovaes.appnews.ui.fragments.webview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gabrielnovaes.appnews.data.local.model.Article
import br.com.gabrielnovaes.appnews.repository.NewsRepository
import kotlinx.coroutines.launch

class WebViewModel constructor(val repository: NewsRepository) : ViewModel() {
    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.updateInsert(article)
    }
}