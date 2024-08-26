package com.example.demoo.data.remote

import com.example.demoo.data.model.Udata
import retrofit2.Response


import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/search/users")
suspend fun searchUsers(
        @Query("q") query: String ,
@Query ("page") page :Int
): Response<Udata>

}