package com.example.todoapp.presentation.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.ui.theme.BlueBgTwo

@Composable
fun CustomDrawer(
    selectedNavigationItem: NavigationItem,
    onNavigationItemClick: (NavigationItem) -> Unit,
    onCloseClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .fillMaxHeight()
            .fillMaxWidth(fraction = 1f)
            .background(BlueBgTwo),
        verticalArrangement = Arrangement.Center,

        ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.7f)
                .fillMaxHeight(fraction = 0.85f)
                .padding(start = 50.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onCloseClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "Back arrow icon",
                        tint = Color.White
                    )
                }
            }

            Column {

                Box(modifier = Modifier.size(135.dp)
                    .clip(CircleShape)
                    .background(Color.Magenta), contentAlignment = Alignment.Center) {
                    Image(
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape),
                        painter = painterResource(R.drawable.e3zaq33rfn791),
                        contentDescription = "profile photo"
                    )
                }

                Spacer(modifier = Modifier.size(15.dp))

                Text(
                    "Gojo Satoru", fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(end = 25.dp)
                )
            }


            Spacer(modifier = Modifier.size(30.dp))

            NavigationItem.entries.toTypedArray().take(4).forEach { navigationItem ->
                NavigationItemView(
                    navigationItem = navigationItem,
                    selected = navigationItem == selectedNavigationItem
                ) { onNavigationItemClick(navigationItem) }

                Spacer(modifier = Modifier.size(4.dp))
            }

            NavigationItem.entries.toTypedArray().takeLast(0).forEach { navigationItem ->
                NavigationItemView(
                    navigationItem = navigationItem,
                    selected = false
                ) {
                    when(navigationItem){
                        NavigationItem.Menu -> {
                            onNavigationItemClick(NavigationItem.Menu)
                        }
                        NavigationItem.Categories -> {
                            onNavigationItemClick(NavigationItem.Categories)

                        }
                        NavigationItem.Analytics -> {
                            onNavigationItemClick(NavigationItem.Analytics)

                        }

                        NavigationItem.Templates -> {
                            onNavigationItemClick(NavigationItem.Analytics)
                        }
                    }
                }

                Spacer(modifier = Modifier.size(4.dp))
            }

        }
    }
}