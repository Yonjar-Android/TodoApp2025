package com.example.todoapp.presentation.customDrawer

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.presentation.mainScreen.NavigationItem
import com.example.todoapp.presentation.mainScreen.NavigationItemView
import com.example.todoapp.ui.theme.BlueBgTwo
import com.example.todoapp.ui.theme.cyanText
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.ZeroLineProperties

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
                        NavigationItem.Templates -> {
                            onNavigationItemClick(NavigationItem.Templates)

                        }

                        NavigationItem.Analytics -> {
                            onNavigationItemClick(NavigationItem.Analytics)
                        }
                    }
                }

                Spacer(modifier = Modifier.size(4.dp))
            }

            Spacer(modifier = Modifier.size(96.dp))

            Column() {
                LineChart(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction = 0.4f),
                    data = remember {
                        listOf(
                            Line(
                                label = "",
                                values = listOf(28.0, 41.0, 5.0, 10.0, 35.0),
                                color = SolidColor(Color.Magenta),
                                firstGradientFillColor = null,
                                secondGradientFillColor = Color.Transparent,
                                strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                                gradientAnimationDelay = 1000,
                                drawStyle = DrawStyle.Stroke(width = 2.dp),
                            )
                        )
                    },
                    animationMode = AnimationMode.Together(delayBuilder = {
                        it * 500L
                    }),
                    gridProperties = GridProperties(enabled = false),
                    labelProperties = LabelProperties(enabled = false),
                    indicatorProperties = HorizontalIndicatorProperties(enabled = false),
                    zeroLineProperties = ZeroLineProperties(enabled = false),
                    labelHelperProperties = LabelHelperProperties(enabled = false)
                    , dividerProperties = DividerProperties(enabled = false)
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text("Good", color = cyanText)

                Spacer(modifier = Modifier.size(4.dp))

                Text("Consistency", color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold)
            }

        }
    }
}