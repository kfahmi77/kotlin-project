package com.khoirulfahmi.kotlinportfolio

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface HadithService {
    @GET("books/{name}/{number}")
    suspend fun getHadith(@Path("name") name: String, @Path("number") number: Int): HadithResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://api.hadith.gading.dev/"

    val instance: HadithService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HadithService::class.java)
    }
}