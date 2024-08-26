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
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
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
    ) { Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Search bar
        BasicTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .weight(1f) // Make the text field take available space
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .padding(16.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        )

        // Search button
        Button(
            onClick = { viewModel.searchUsers(query) },
            modifier = Modifier
                .align(Alignment.CenterVertically) // Align button vertically center within the row
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Search")
        }
    }
      /*  BasicTextField(
               value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .weight(1f)
               // .fillMaxWidth()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .padding(16.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        ))

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.searchUsers(query) },
            modifier = Modifier
                .align(Alignment.CenterVertically) // Align button vertically center
                .padding(8.dp)) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Search")
        }*/

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
                .size(40.dp)
                .clip(CircleShape) // Clip image to be circular
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), CircleShape) // Optional border

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
