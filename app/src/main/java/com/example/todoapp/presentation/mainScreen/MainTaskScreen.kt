package com.example.todoapp.presentation.mainScreen

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.data.mapper.TaskMapper
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.data.models.TaskWithCategoryColor
import com.example.todoapp.presentation.categories.CategoriesScreen
import com.example.todoapp.presentation.customDrawer.CustomDrawer
import com.example.todoapp.presentation.customDrawer.CustomDrawerState
import com.example.todoapp.presentation.customDrawer.isOpened
import com.example.todoapp.presentation.customDrawer.opposite
import com.example.todoapp.ui.theme.BlueBg
import com.example.todoapp.ui.theme.BlueBgTwo
import com.example.todoapp.ui.theme.PinkButton
import com.example.todoapp.ui.theme.cyanText
import com.example.todoapp.utils.SoundPlayer
import kotlin.math.roundToInt

@Composable
fun MainTaskScreen(viewModel: MainScreenViewModel) {

    val navController = rememberNavController()

    val loading by viewModel.isLoading.collectAsState()

    val message by viewModel.message.collectAsState()

    val tasks by viewModel.tasks.collectAsState()

    val categories by viewModel.categories.collectAsState()

    val categoryId by viewModel.categoryId.collectAsState()

    val taskSearch by viewModel.taskSearch.collectAsState()

    val context = LocalContext.current

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Red)
        }
    }

    if (message?.isNotEmpty() == true) {
        Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
        viewModel.resetMessages()
    }

    var showCreateTaskScreen by remember { mutableStateOf(false) }

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

                when (it) {
                    NavigationItem.Menu -> navController.navigate("mainContent")
                    NavigationItem.Categories -> navController.navigate("categories")
                    /*NavigationItem.Analytics -> navController.navigate("analytics")*/
                    else -> navController.navigate("mainContent")
                }
            },
            onCloseClick = {
                drawerState = CustomDrawerState.Closed
            }
        )

        val modifierEdit = Modifier
            .offset(x = animatedOffset)
            .scale(scale = animatedScale)
            .clip(RoundedCornerShape(24.dp))
            .systemBarsPadding()
            .background(BlueBg)

        NavHost(navController = navController, startDestination = "mainContent") {
            composable("mainContent") {
                MainContent(
                    modifier = modifierEdit,
                    tasks = tasks,
                    taskSearch = taskSearch,
                    categoryId = categoryId,
                    categories = categories,
                    viewModel = viewModel,
                    showTaskScreen = { showCreateTaskScreen = !showCreateTaskScreen },
                    drawerState = drawerState,
                    onDrawerState = { drawerState = it },
                    context = context
                )
            }

            composable("categories") {
                CategoriesScreen(
                    modifier = modifierEdit,
                    drawerState = drawerState,
                    onDrawerState = { drawerState = it },
                    categories = categories,
                    viewModel = viewModel
                )
            }
        }
    }

    if (showCreateTaskScreen) {
        TaskCreateScreen(categories = categories,
            viewModel = viewModel,
            onDismiss = { showCreateTaskScreen = false })
    }

}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    tasks: List<TaskWithCategoryColor>,
    taskSearch: String?,
    categoryId: Long?,
    categories: List<CategoryWithTaskCount>,
    viewModel: MainScreenViewModel,
    showTaskScreen: () -> Unit,
    drawerState: CustomDrawerState,
    onDrawerState: (CustomDrawerState) -> Unit,
    context: Context
) {
    Box(
        modifier = modifier
            .fillMaxSize()
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

            FirstIconRow(drawerState, onDrawerState, viewModel)

            Text(
                stringResource(R.string.greetingStr,"Juan"),
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 8.dp)
            )

            Spacer(modifier = Modifier.size(24.dp))

            CategoriesRow(categories, viewModel)

            Spacer(modifier = Modifier.size(24.dp))

            TasksColumn(
                tasks,
                viewModel = viewModel,
                categoryId,
                taskSearch = taskSearch,
                context = context
            )
        }

        Button(
            onClick = {
                showTaskScreen()
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
    onDrawerState: (CustomDrawerState) -> Unit,
    viewModel: MainScreenViewModel
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

        var searchQuery by remember { mutableStateOf("") }

        Row(
            modifier = Modifier, verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.weight(0.1f))

            // TextField (centrado)
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .weight(2f) // Ajusta el peso para centrar el TextField
                    .clip(RoundedCornerShape(20.dp))
                    .height(60.dp),
                placeholder = { Text(stringResource(R.string.searchStr), color = Color.White) },
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
                size = 22.dp, idImage = R.drawable.search, iconDescription = "Search bar icon",
                modifier = Modifier.padding(horizontal = 8.dp), funcClick = {
                    viewModel.taskSearch.value = searchQuery
                }
            )

            IconFunc(
                size = 28.dp, idImage = R.drawable.bell, iconDescription = "Notification bell icon",
                modifier = Modifier.padding(horizontal = 8.dp),
                funcClick = {}
            )

        }
    }
}

@Composable
fun CategoriesRow(
    categories: List<CategoryWithTaskCount>,
    viewModel: MainScreenViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp)
    ) {
        Text(
            stringResource(R.string.categoriesStr),
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

            items(categories, key = { cat -> cat.id }) {
                CategoriesItem(it, loadTasksByCategory = { categoryId ->
                    if (viewModel.categoryId.value == categoryId) {
                        viewModel.categoryId.value = null
                        viewModel.taskSearch.value = null
                    } else {
                        viewModel.categoryId.value = categoryId
                        viewModel.taskSearch.value = null
                    }
                })

                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
fun CategoriesItem(
    categoryModel: CategoryWithTaskCount,
    loadTasksByCategory: (Long) -> Unit
) {

    val progress =
        if (categoryModel.pendingTasks != 0)
            categoryModel.completedTasks.toFloat() / categoryModel.totalTasks.toFloat()
        else 1f

    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(14.dp))
            .background(BlueBgTwo)
            .clickable {
                loadTasksByCategory(categoryModel.id)
            }
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Text(
                text = "${categoryModel.pendingTasks} ${stringResource(R.string.tasksStr)}",
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
                        .background(
                            color = Color(0xFF000000 or categoryModel.color.toLong()),
                            RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun TasksColumn(
    tasks: List<TaskWithCategoryColor>,
    viewModel: MainScreenViewModel,
    categoryId: Long?,
    taskSearch: String?,
    context: Context
) {
    Column(modifier = Modifier) {
        Text(
            stringResource(R.string.todaysTask),
            color = cyanText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 20.dp)
        )

        Spacer(modifier = Modifier.size(8.dp))

        val filteredTasks = tasks.filter { task ->
            val matchesCategory = categoryId == null || task.categoryId == categoryId
            val matchesSearch =
                taskSearch.isNullOrEmpty() || task.title.contains(taskSearch, ignoreCase = true)
            matchesCategory && matchesSearch
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(filteredTasks, key = { it.id }) {
                TasksItem(it, viewModel, context = context)
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
fun TasksItem(taskItem: TaskWithCategoryColor, viewModel: MainScreenViewModel, context: Context) {

    var checkValue by rememberSaveable { mutableStateOf(taskItem.completed) }

    var isMenuVisible by remember { mutableStateOf(false) }

    var deleteDialogVisible by remember { mutableStateOf(false) }

    var editDialogVisible by remember { mutableStateOf(false) }

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
                    .border(
                        2.dp,
                        Color(0xFF000000 or taskItem.categoryColor.toLong()),
                        RoundedCornerShape(16.dp)
                    )  // Borde redondeado
            ) {
                Checkbox(
                    checked = checkValue,
                    onCheckedChange = {
                        if (it) {
                            SoundPlayer.playSound(context, R.raw.dingsound)
                        }
                        checkValue = it
                        viewModel.updateTask(
                            TaskMapper.toTaskModelFromTaskColor(
                                taskItem.copy(
                                    completed = it,
                                    completedDate = System.currentTimeMillis()
                                )
                            )
                        )
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Transparent, // Hace que la casilla desaparezca
                        checkmarkColor = Color.White  // Color de la marca al estar activo
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

            Spacer(modifier = Modifier.weight(1f))

            Box {


                IconButton(modifier = Modifier.size(30.dp),
                    onClick = {
                        isMenuVisible = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Sharp.MoreVert, contentDescription = "3 points Icon",
                        tint = Color.White
                    )
                }

                DropDownMenuTaskItem(
                    isVisible = isMenuVisible,
                    onVisibleChange = { isMenuVisible = it },
                    onEdit = {
                        editDialogVisible = !editDialogVisible
                    },
                    onDelete = {
                        deleteDialogVisible = !deleteDialogVisible
                    }
                )

                if (editDialogVisible) {
                    EditTaskDialog(
                        taskItem.title,
                        onEdit = { newText ->
                            viewModel.updateTask(
                                TaskMapper.toTaskModelFromTaskColor(taskItem)
                                    .copy(title = newText)
                            )
                        },
                        onDismissDialog = { editDialogVisible = !editDialogVisible }
                    )
                }

                if (deleteDialogVisible) {
                    DeleteDialog(
                        onDelete = {
                            viewModel.deleteTask(
                                TaskMapper.toTaskModelFromTaskColor(taskItem)
                            )
                        },
                        onDismissDialog = { deleteDialogVisible = !deleteDialogVisible },
                        editText = stringResource(R.string.deleteTaskMsg)
                    )
                }
            }
        }
    }
}

@Composable
fun EditTaskDialog(
    currentText: String,
    onDismissDialog: () -> Unit,
    onEdit: (String) -> Unit
) {
    var editedText by remember { mutableStateOf(currentText) }

    AlertDialog(
        containerColor = BlueBg,
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = {
                    onEdit(editedText)
                    onDismissDialog()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                )
            ) {
                Text(stringResource(R.string.saveStr), color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismissDialog() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF455A64)
                )
            ) {
                Text(stringResource(R.string.cancelStr), color = Color.White)
            }
        },
        title = {
            Text(
                stringResource(R.string.editTaskMsg),
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        },
        text = {
            Column {
                TextField(
                    value = editedText,
                    onValueChange = { editedText = it }, // Actualiza el estado con el nuevo texto
                    label = { Text(stringResource(R.string.newTextMsg), fontSize = 14.sp) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color.White.copy(alpha = 0.7f),
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                        cursorColor = Color.White
                    ),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp, // Tamaño de fuente más grande
                        color = Color.White // Color del texto
                    )
                )
            }
        }
    )
}

@Composable
fun DeleteDialog(
    editText: String,
    onDismissDialog: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        containerColor = BlueBg,
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = {
                    onDelete.invoke()
                    onDismissDialog.invoke()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC62828)
                )
            ) {
                Text(stringResource(R.string.deleteStr))
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismissDialog.invoke() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF455A64)
                )
            ) {
                Text(stringResource(R.string.cancelStr), color = Color.White)
            }
        },
        title = {
            Text(
                editText,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    )
}

@Composable
fun DropDownMenuTaskItem(
    isVisible: Boolean,
    onVisibleChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {

    DropdownMenu(
        expanded = isVisible,
        onDismissRequest = { onVisibleChange(false) }
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.deleteStr)) },
            onClick = {
                onVisibleChange(false)
                onDelete()
            }
        )

        DropdownMenuItem(
            text = { Text(stringResource(R.string.editStr)) },
            onClick = {
                onVisibleChange(false)
                onEdit()
            }
        )
    }
}

@Composable
fun IconFunc(
    idImage: Int,
    size: Dp,
    iconDescription: String,
    modifier: Modifier = Modifier,
    funcClick: () -> Unit
) {
    Box(modifier = modifier) {
        IconButton(onClick = {
            funcClick.invoke()
        }) {
            Icon(
                modifier = Modifier
                    .size(size),
                painter = painterResource(id = idImage),
                contentDescription = iconDescription,
                tint = Color.White
            )
        }

    }
}

