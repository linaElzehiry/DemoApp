package com.example.demoo.data.remote


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "https://api.github.com/"

    private var retrofit: Retrofit? = null

    fun getService(): Api {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // Use Gson for parsing JSON
                .client(OkHttpClient.Builder().build()) // Add OkHttpClient for customization (optional)
                .build()
        }
        return retrofit!!.create(Api::class.java)
    }
}