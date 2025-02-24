package com.example.todoapp.presentation.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.presentation.customDrawer.CustomDrawerState
import com.example.todoapp.presentation.customDrawer.opposite
import com.example.todoapp.presentation.mainScreen.IconFunc

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    drawerState: CustomDrawerState,
    onDrawerState: (CustomDrawerState) -> Unit,
    categories: List<CategoryWithTaskCount>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                onDrawerState(CustomDrawerState.Closed)
            }

    ) {
        IconRowCategories(drawerState, onDrawerState)

        LazyColumn {
            items(categories){
                Text(it.title)
            }
        }
    }
}

@Composable
fun IconRowCategories(
    drawerState: CustomDrawerState,
    onDrawerState: (CustomDrawerState) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically // Centra verticalmente todos los elementos del Row
    ) {
        // Ícono del menú (izquierda)
        IconButton(onClick = {
            onDrawerState(drawerState.opposite())
        }) {
            Icon(
                modifier = Modifier
                    .size(22.dp)
                    .padding(),
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "Menu icon",
                tint = Color.White
            )
        }

        // Espacio flexible entre el ícono del menú y el TextField
        Spacer(modifier = Modifier.weight(0.1f))

        // TextField (centrado)
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .weight(2f) // Ajusta el peso para centrar el TextField
                .clip(RoundedCornerShape(20.dp))
                .height(60.dp),
            placeholder = { Text("Buscar...", color = Color.White) },
            singleLine = true,
            colors = androidx.compose.material3.TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
            )
        )

        IconFunc(
            size = 22.dp,
            idImage = R.drawable.search,
            iconDescription = "Search bar icon",
            funcClick = {

            }
        )
    }
}