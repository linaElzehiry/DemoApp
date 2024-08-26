package com.example.demoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demoo.View.SearchUserListItem
import com.example.demoo.View.SearchUsersViewModel
import com.example.demoo.data.model.Item
import com.example.demoo.data.repo.UserRepository
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val searchUsersViewModel: SearchUsersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        var searchQuery by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search users") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            searchUsersViewModel.searchUsers(searchQuery)
            val searchResult by searchUsersViewModel.searchUsersResult.observeAsState(initial = null)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                searchResult?.items?.let { users ->
                    items(users) { user ->
                        SearchUserListItem(user = user)
                    }
                }
            }
        }
    }
}