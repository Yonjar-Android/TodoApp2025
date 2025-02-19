package com.example.todoapp.presentation.mainScreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.TaskModel
import com.example.todoapp.presentation.customDrawer.CustomDrawer
import com.example.todoapp.presentation.customDrawer.CustomDrawerState
import com.example.todoapp.presentation.customDrawer.isOpened
import com.example.todoapp.presentation.customDrawer.opposite
import com.example.todoapp.ui.theme.BlueBg
import com.example.todoapp.ui.theme.BlueBgTwo
import com.example.todoapp.ui.theme.PinkButton
import com.example.todoapp.ui.theme.cyanText
import kotlin.math.roundToInt

@Composable
fun MainTaskScreen(viewModel: MainScreenViewModel) {

    val state by viewModel.state.collectAsState()

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Red)
        }
    }

    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
    var selectedNavigationItem by remember { mutableStateOf(NavigationItem.Menu) }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density

    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * density).roundToInt() }
    }

    val offsetValue by remember { derivedStateOf { (screenWidth.value / 3.5).dp } }

    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
        label = "Animated Offset"
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) 0.9f else 1f,
        label = "Animated Scale"
    )

    BackHandler(enabled = drawerState.isOpened()) {
        drawerState = CustomDrawerState.Closed
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        CustomDrawer(
            selectedNavigationItem = selectedNavigationItem,
            onNavigationItemClick = {
                selectedNavigationItem = it
            },
            onCloseClick = {
                drawerState = CustomDrawerState.Closed
            }
        )

        MainContent(modifier =
        Modifier
            .offset(x = animatedOffset)
            .scale(scale = animatedScale),
            tasks = state.tasks,
            categories = state.categories,
            viewModel = viewModel,
            drawerState,
            onDrawerState = { drawerState = it })
    }

}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    tasks: List<TaskModel>,
    categories: List<CategoryModel>,
    viewModel: MainScreenViewModel,
    drawerState: CustomDrawerState,
    onDrawerState: (CustomDrawerState) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.Red)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BlueBg)
                .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                    onDrawerState(CustomDrawerState.Closed)
                }
                .clip(RoundedCornerShape(24.dp))
        ) {

            FirstIconRow(drawerState, onDrawerState)

            Text(
                "What's up, Juan!",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
            )

            CategoriesRow(categories)

            Spacer(modifier = Modifier.size(24.dp))

            TasksColumn(tasks)
        }

        Button(
            onClick = {

                val task = TaskModel(
                    title = "Tarea de prueba",
                    createdDate = 0,
                    completedDate = 0,
                    categoryId = 1
                )
                viewModel.createTask(task)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 24.dp)
                .size(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PinkButton)
        ) {
            Text(
                "+", fontSize = 32.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun FirstIconRow(
    drawerState: CustomDrawerState,
    onDrawerState: (CustomDrawerState) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

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
fun CategoriesRow(categories: List<CategoryModel>) {
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

        Spacer(modifier = Modifier.size(8.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(categories) {
                CategoriesItem(it)
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
fun CategoriesItem(categoryModel: CategoryModel) {

    val progress = if (true) 5f / 30f else 0f

    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(14.dp))
            .background(BlueBgTwo)

    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Text(
                text = "10 tasks",
                color = Color.LightGray,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = categoryModel.title,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )

            // Barra de progreso
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(170.dp)
                    .background(Color.Gray, RoundedCornerShape(4.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .height(4.dp)
                        .background(Color.Magenta, RoundedCornerShape(4.dp))
                )
            }

        }
    }
}

@Composable
fun TasksColumn(tasks: List<TaskModel>) {
    Column(modifier = Modifier) {
        Text(
            "TODAY'S TASKS",
            color = cyanText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 20.dp)
        )

        Spacer(modifier = Modifier.size(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(tasks) {
                TasksItem(it)
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
fun TasksItem(taskItem: TaskModel) {

    var checkValue by rememberSaveable { mutableStateOf(taskItem.completed) }

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
                    checked = checkValue,
                    onCheckedChange = { checkValue = it },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Transparent, // Hace que la casilla desaparezca
                        checkmarkColor = Color.Magenta  // Color de la marca al estar activo
                    )
                )
            }

            Text(
                taskItem.title,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                textDecoration = if (checkValue) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                }
            )
        }
    }
}


@Composable
fun IconFunc(
    idImage: Int,
    size: Dp,
    iconDescription: String,
) {
    Box(modifier = Modifier.padding(horizontal = 8.dp)) {
        IconButton(onClick = {

        }) {
            Icon(
                modifier = Modifier
                    .size(size)
                    .padding(),
                painter = painterResource(id = idImage),
                contentDescription = iconDescription,
                tint = Color.White
            )
        }

    }
}