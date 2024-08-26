package com.example.demoo.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoo.data.model.Item
import com.example.demoo.data.model.Udata
import com.example.demoo.data.repo.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<Item>>()
    val users: LiveData<List<Item>> get() = _users

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun searchUsers(query: String, page: Int = 1) {
        viewModelScope.launch {
            try {
                val response: Response<Udata> = userRepository.searchUsers(query, page)
                if (response.isSuccessful) {
                    _users.postValue(response.body()?.items ?: emptyList())
                } else {
                    _error.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Exception: ${e.message}")
            }
        }
    }
}
