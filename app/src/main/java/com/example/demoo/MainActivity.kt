package com.example.demoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.demoo.View.UserViewModel
import com.example.demoo.View.ViewModelFactory
import com.example.demoo.data.model.Item
import com.example.demoo.data.remote.RetrofitHelper
import com.example.demoo.data.repo.UserRepository
import com.example.demoo.ui.theme.DemooTheme
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemooTheme {
                // Provide your UserViewModel
                val userViewModel: UserViewModel = viewModel(
                    factory = ViewModelFactory(UserRepository(RetrofitHelper.getService()))
                )
                SearchScreen(userViewModel)
            }
        }
    }
}

@Composable
fun SearchScreen(viewModel: UserViewModel) {
    var query by remember { mutableStateOf("") }
    val users by viewModel.users.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.searchUsers(query) }) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            error != null -> Text("Error: $error", color = MaterialTheme.colorScheme.error)
            users.isEmpty() -> Text("No results found", fontSize = 18.sp)
            else -> {
                LazyColumn {
                    items(users) { user ->
                        UserItem(user)
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: Item) {
    Row(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = rememberAsyncImagePainter(model =user.avatar_url ),
            contentDescription = "User Avatar",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(user.login, fontSize = 18.sp)
            Text(user.html_url, fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    DemooTheme {
        SearchScreen(viewModel = UserViewModel(UserRepository(RetrofitHelper.getService())))
    }
}
