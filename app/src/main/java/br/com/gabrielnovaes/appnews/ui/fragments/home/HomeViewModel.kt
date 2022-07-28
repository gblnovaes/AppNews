package br.com.gabrielnovaes.appnews.ui.fragments.home

import androidx.lifecycle.ViewModel
import br.com.gabrielnovaes.appnews.repository.NewsRepository

class HomeViewModel constructor(private val repository:NewsRepository) : ViewModel() {
}