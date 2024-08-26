package com.example.demoo.data.repo

import com.example.demoo.data.model.Udata
import com.example.demoo.data.remote.Api
import retrofit2.Response

class UserRepository(private val api: Api) {

    suspend fun searchUsers(query: String, page: Int = 1): Response<Udata> {
        return api.searchUsers(query, page)
    }
}