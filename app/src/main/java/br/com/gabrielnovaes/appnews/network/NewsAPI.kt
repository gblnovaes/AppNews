package br.com.gabrielnovaes.appnews.network

import br.com.gabrielnovaes.appnews.model.NewsResponse
import br.com.gabrielnovaes.appnews.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode : String = "br",
        @Query("pages") pageNumber : Int = 1,
        @Query("apiKey") apiKey : String = API_KEY
    ):Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("p") searchQuery : String,
        @Query("pages") pageNumber : Int = 1,
        @Query("apiKey") apiKey : String = API_KEY
    ):Response<NewsResponse>

}