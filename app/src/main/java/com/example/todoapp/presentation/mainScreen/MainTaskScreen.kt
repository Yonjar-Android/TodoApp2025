package com.example.todoapp.presentation.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.ui.theme.BlueBg
import com.example.todoapp.ui.theme.BlueBgTwo
import com.example.todoapp.ui.theme.cyanText

@Composable
fun MainTaskScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BlueBg)
                .systemBarsPadding()
        ) {

            FirstIconRow()

            Text(
                "What's up, Juan!",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
            )

            CategoriesRow()

            TasksColumn()
        }

        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
                .size(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
        ) {
            Text("+", fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal)
        }
    }
}

@Composable
fun FirstIconRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp)
    ) {
        Text(
            "CATEGORIES",
            color = cyanText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(3) {
                CategoriesItem()
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
fun CategoriesItem() {

    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(14.dp))
            .background(BlueBgTwo)

    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
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
fun TasksColumn() {
    Column(modifier = Modifier) {
        Text(
            "TODAY'S TASKS",
            color = cyanText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(5) {
                TasksItem()
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
fun TasksItem() {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(BlueBgTwo)
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)  // Tamaño del checkbox
                    .clip(RoundedCornerShape(16.dp))  // Bordes redondeados
                    .border(2.dp, Color.Magenta, RoundedCornerShape(16.dp))  // Borde redondeado
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Transparent, // Hace que la casilla desaparezca
                        checkmarkColor = Color.Magenta  // Color de la marca al estar activo
                    )
                )
            }

            Text(
                "Daily meeting with team",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}


@Composable
fun IconFunc(idImage: Int, size: Dp, iconDescription: String) {
    Box(modifier = Modifier.padding(horizontal = 8.dp)) {
        Icon(
            modifier = Modifier
                .size(size)
                .padding(),
            painter = painterResource(id = idImage),
            contentDescription = iconDescription,
            tint = Color.White,

            )
    }

}