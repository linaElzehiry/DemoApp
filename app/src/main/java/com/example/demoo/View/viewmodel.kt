package com.example.demoo.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.demoo.data.model.Udata
import com.example.demoo.data.repo.UserRepository
import kotlinx.coroutines.launch

class SearchUsersViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _searchUsersResult = MutableLiveData<Udata>()
    val searchUsersResult: LiveData<Udata> = _searchUsersResult

    fun searchUsers(query: String, page: Int = 1) {
        viewModelScope.launch {
            val response = userRepository.searchUsers(query, page)
            if (response.isSuccessful) {
                _searchUsersResult.value = response.body()!!
            } else {
                // Handle API errors (optional)
            }
        }
    }
}