package com.example.demoo.View

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.example.demoo.data.model.Item

@Composable
fun SearchUserListItem(user: Item) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Handle user click (optional)
            },
        color = Color.LightGray
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = user.login,)}}}

