package br.com.gabrielnovaes.appnews.ui.fragments.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.gabrielnovaes.appnews.repository.NewsRepository
import br.com.gabrielnovaes.appnews.ui.fragments.favorite.FavoriteViewModel
import br.com.gabrielnovaes.appnews.ui.fragments.home.HomeViewModel
import br.com.gabrielnovaes.appnews.ui.fragments.search.SearchViewModel


class ViewModelFactory(
    private val repository: NewsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository) as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(repository) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(repository) as T
            else -> throw IllegalArgumentException("ViewModel Not Found..")
        }
    }
}