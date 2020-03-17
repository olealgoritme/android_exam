package com.codehunterz.isail.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private const val BASE_URL = "https://www.noforeignland.com/home/api/v1/"
    private var retrofit: Retrofit? = null
    val getIt: Retrofit?
        get() {
            val client = OkHttpClient.Builder().build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit
        }
}