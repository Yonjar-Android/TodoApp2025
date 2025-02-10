package com.example.todoapp.presentation.mainScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R

@Composable
fun MainTaskScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
            .systemBarsPadding()
    ) {
        FirstIconRow()

        Text(
            "What's up, Juan",
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.SemiBold
        )

        CategoriesRow()

        TasksColumn()
    }
}

@Composable
fun FirstIconRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconFunc(
            size = 28.dp, idImage = R.drawable.menu, iconDescription = "Menu icon"
        )

        Row(
            modifier = Modifier, verticalAlignment = Alignment.CenterVertically
        ) {

            IconFunc(
                size = 22.dp, idImage = R.drawable.search, iconDescription = "Search bar icon"
            )

            IconFunc(
                size = 28.dp, idImage = R.drawable.bell, iconDescription = "Notification bell icon"
            )

        }
    }
}

@Composable
fun CategoriesRow() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "CATEGORIES",
            color = Color.LightGray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        LazyRow {
            items(1) {
                CategoriesItem()
            }
        }
    }
}

@Composable
fun CategoriesItem() {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(14.dp))
            .background(Color.DarkGray)

    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            ) {
            Text(
                text = "10 tasks",
                color = Color.LightGray,
                fontSize = 16.sp
            )

            Text(
                text = "Business",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun TasksColumn(){
    Column {
        Text(
            "TODAY'S TASKS",
            color = Color.LightGray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        LazyColumn {
            items(1){
                TasksItem()
            }
        }
    }
}

@Composable
fun TasksItem(){
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = false,
            onCheckedChange = {}
        )

        Text("Daily meeting with team",
            color = Color.White,
            fontSize = 14.sp)
    }
}


@Composable
fun IconFunc(idImage: Int, size: Dp, iconDescription: String) {
    Icon(
        modifier = Modifier.size(size),
        painter = painterResource(id = idImage),
        contentDescription = iconDescription,
        tint = Color.White
    )
}