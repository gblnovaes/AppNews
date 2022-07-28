package br.com.gabrielnovaes.appnews.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.gabrielnovaes.appnews.data.local.model.NewsResponse
import br.com.gabrielnovaes.appnews.repository.NewsRepository
import br.com.gabrielnovaes.appnews.util.state.StateResource
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel constructor(private val repo: NewsRepository) : ViewModel() {

    private val _getAll = MutableLiveData<StateResource<NewsResponse>>()
    val getAll: LiveData<StateResource<NewsResponse>> get() = _getAll

    init {
        fetchAll()
    }

    private fun fetchAll()  =  viewModelScope.launch {
        safeFetchAll()
    }

    private suspend fun safeFetchAll() {
        _getAll.value = StateResource.Loading()

        try{
            val response = repo.getAllRemote()
            _getAll.value = handleResponse(response)
        }catch (e : Exception){

        }
    }

    private fun handleResponse(response: Response<NewsResponse>): StateResource<NewsResponse>? {
        if (response.isSuccessful){
            response.body()?.let { values ->
                return StateResource.Success(values)
            }
        }
        return StateResource.Error(response.message())
    }

}
