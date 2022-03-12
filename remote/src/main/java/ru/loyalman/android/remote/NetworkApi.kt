package ru.loyalman.android.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.loyalman.android.remote.di.Page

interface NetworkApi {


    @GET("results")
    fun searchYoutube(
        @Query("search_query")
        query: String,
    ): Call<Page?>
}