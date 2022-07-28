package br.com.gabrielnovaes.appnews.ui.fragments.favorite

import androidx.lifecycle.*
import br.com.gabrielnovaes.appnews.data.local.model.Article
import br.com.gabrielnovaes.appnews.repository.NewsRepository
import br.com.gabrielnovaes.appnews.util.state.ArticleListEvent
import br.com.gabrielnovaes.appnews.util.state.ArticleListState
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _favorite  = MutableLiveData<ArticleListEvent>()

    val favorite : LiveData<ArticleListState> = _favorite.switchMap {
        when(it){
            ArticleListEvent.Fetch -> getAll()
        }
    }

    fun dispatch(event: ArticleListEvent){
        this._favorite.postValue(event)
    }

    private fun getAll(): LiveData<ArticleListState>? {
        return liveData {
            try{
                emit(ArticleListState.Loading)
                val source = repository.getAllArticles()
                    .map { list->
                        if(list.isEmpty()){
                            ArticleListState.Empty
                        }else{
                            ArticleListState.Success(list)
                        }
                    }

                emitSource(source)

            }catch (e : Exception){
                emit(ArticleListState.ErrorMessage("Error  ${e.message}"))
            }
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.updateInsert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }



}