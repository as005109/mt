package com.lisovskaya.lab3.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {

    @GET("/")
    fun getResultsForString(
        @Query("s") partialName: String,
        @Query("apikey") apiKey: String = "c8d2a36b"
    ): Single<OmdbSearchResult>
}