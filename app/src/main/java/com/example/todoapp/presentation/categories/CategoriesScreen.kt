package com.example.todoapp.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.data.mapper.CategoryMapper
import com.example.todoapp.data.models.CategoryModel
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.presentation.customDrawer.CustomDrawerState
import com.example.todoapp.presentation.customDrawer.opposite
import com.example.todoapp.presentation.mainScreen.DeleteDialog
import com.example.todoapp.presentation.mainScreen.IconFunc
import com.example.todoapp.presentation.mainScreen.MainScreenViewModel
import com.example.todoapp.ui.theme.BlueBg
import com.example.todoapp.ui.theme.BlueBgTwo
import com.example.todoapp.ui.theme.PinkButton
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    drawerState: CustomDrawerState,
    onDrawerState: (CustomDrawerState) -> Unit,
    categories: List<CategoryWithTaskCount>,
    viewModel: MainScreenViewModel
) {

    var createDialogVisible by remember { mutableStateOf(false) }

    val categorySearch by viewModel.categorySearch.collectAsState()

    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                    onDrawerState(CustomDrawerState.Closed)
                }
                .testTag("categoryScreen")

        ) {
            IconRowCategories(drawerState, onDrawerState, onSearch =  {
                if (it.isEmpty()){
                    viewModel.categorySearch.value = null
                } else{
                    viewModel.categorySearch.value = it
                }

            })

            Spacer(modifier = Modifier.size(16.dp))

            LazyColumn {
                val filteredCategories = categories.filter {
                    categorySearch == null || it.title.lowercase().contains(categorySearch!!)
                }

                items(filteredCategories, key = {cat -> cat.id}) {
                    ItemCategory(it, viewModel = viewModel)

                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }

        Button(
            onClick = {
                createDialogVisible = !createDialogVisible
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 24.dp)
                .size(64.dp)
                .testTag("btnCreate"),
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

    if (createDialogVisible) {
        CreateCategory(
            onCreateCategory = { categoryName, color ->
                viewModel.createCategory(
                    CategoryModel(title = categoryName, color = color)
                )
            },
            onDismissDialog = {
                createDialogVisible = !createDialogVisible
            }
        )
    }

}

@Composable
fun ItemCategory(
    category: CategoryWithTaskCount,
    viewModel: MainScreenViewModel // Si necesitas el ViewModel para acciones
) {
    var isMenuVisible by remember { mutableStateOf(false) }
    var deleteDialogVisible by remember { mutableStateOf(false) }
    var editDialogVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)) // Bordes redondeados
                .background(BlueBgTwo) // Fondo azul
                .padding(24.dp), // Padding interno
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nombre de la categoría
            Text(
                text = category.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.weight(1f) // Ocupa el espacio disponible
            )

            // Ícono de tres puntos
            Box {
                IconButton(
                    modifier = Modifier.size(30.dp)
                        .testTag("btnOptions${category.id}"),
                    onClick = { isMenuVisible = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Opciones",
                        tint = Color.White
                    )
                }

                // Menú desplegable
                DropDownMenuCategories(
                    idCat = category.id,
                    isVisible = isMenuVisible,
                    onVisibleChange = { isMenuVisible = it },
                    onEdit = { editDialogVisible = true },
                    onDelete = { deleteDialogVisible = true }

                )
            }
        }
    }

    if (deleteDialogVisible) {
        DeleteDialog(
            editText = stringResource(R.string.deleteCatMsg),
            onDelete = {
                viewModel.deleteCategory(
                    CategoryMapper.fromCategoryCountTaskToCategoryModel(category)
                )

            },
            onDismissDialog = { deleteDialogVisible = false }
        )
    }

    if (editDialogVisible) {
        EditCategoriesDialog(
            currentText = category.title,
            currentColor = category.color,
            onEdit = { newTitle, newColor ->
                viewModel.updateCategory(
                    CategoryMapper.fromCategoryCountTaskToCategoryModel(
                        category.copy(
                            title = newTitle,
                            color = newColor
                        )
                    )
                )
            },
            onDismissDialog = { editDialogVisible = false }
        )
    }

}

@Composable
fun IconRowCategories(
    drawerState: CustomDrawerState,
    onDrawerState: (CustomDrawerState) -> Unit,
    onSearch: (String) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically // Centra verticalmente todos los elementos del Row
    ) {
        // Ícono del menú (izquierda)
        IconButton(
            modifier = Modifier.testTag("btnMenuDrawer"),
            onClick = {
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
            size = 22.dp,
            idImage = R.drawable.search,
            iconDescription = "Search bar icon",
            funcClick = {
                    onSearch(searchQuery.trim().lowercase())
            }
        )
    }
}

@Composable
fun DropDownMenuCategories(
    idCat: Long,
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
            modifier = Modifier.testTag("btnMenuDelete$idCat"),
            text = { Text(stringResource(R.string.deleteStr)) },
            onClick = {
                onVisibleChange(false)
                onDelete()
            }
        )

        DropdownMenuItem(
            modifier = Modifier.testTag("btnMenuEdit$idCat"),
            text = { Text(stringResource(R.string.editStr)) },
            onClick = {
                onVisibleChange(false)
                onEdit()
            }
        )
    }
}

@Composable
fun CreateCategory(
    onDismissDialog: () -> Unit,
    onCreateCategory: (String, Int) -> Unit,
) {
    var categoryName by remember { mutableStateOf("") }

    var colorChange by remember { mutableStateOf(Color(0xFF000000)) }


    AlertDialog(
        modifier = Modifier.testTag("createCategoryDialog"),
        containerColor = BlueBg,
        onDismissRequest = {},
        confirmButton = {
            Button(
                modifier = Modifier.testTag("btnCreateTask"),
                onClick = {
                    onCreateCategory(categoryName, colorChange.toArgb())
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
                stringResource(R.string.createCategorieName),
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        },
        text = {
            Column {
                TextField(
                    modifier = Modifier.testTag("tfCategory"),
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text(stringResource(R.string.categorieNameMsg), fontSize = 14.sp) },
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
                        fontSize = 18.sp,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.size(32.dp))

                val controller = rememberColorPickerController()

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        stringResource(R.string.selectColorMsg),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )


                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(10.dp),
                        controller = controller,
                        onColorChanged = { colorEnvelope: ColorEnvelope ->
                            colorChange = colorEnvelope.color
                        }
                    )

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(Color.White), contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(colorChange)
                        )
                    }
                }
            }

        }
    )
}

@Composable
fun EditCategoriesDialog(
    currentText: String,
    currentColor: Int,
    onDismissDialog: () -> Unit,
    onEdit: (String, Int) -> Unit
) {
    var editedText by remember { mutableStateOf(currentText) }

    var color by remember { mutableIntStateOf(currentColor) }

    AlertDialog(
        modifier = Modifier.testTag("editCategoryScreen"),
        containerColor = BlueBg,
        onDismissRequest = {},
        confirmButton = {
            Button(
                modifier = Modifier.testTag("btnEdit"),
                onClick = {
                    onEdit(editedText, color)
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
                stringResource(R.string.editCatMsg),
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        },
        text = {
            Column {
                TextField(
                    modifier = Modifier.testTag("tfEditCategory"),
                    value = editedText,
                    onValueChange = { editedText = it },
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
                        fontSize = 18.sp,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.size(32.dp))

                val controller = rememberColorPickerController()

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        stringResource(R.string.selectColorMsg),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )

                    var colorChange by remember { mutableStateOf(Color(0xFF000000 or currentColor.toLong())) }

                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(10.dp),
                        controller = controller,
                        onColorChanged = { colorEnvelope: ColorEnvelope ->
                            colorChange = colorEnvelope.color
                            color = colorEnvelope.color.toArgb()
                        }
                    )

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(Color.White), contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    if (colorChange == Color(1.0F, 1.0F, 1.0F, 1.0F)) {
                                        Color(0xFF000000 or currentColor.toLong())
                                    } else {
                                        colorChange
                                    }
                                )
                        )
                    }
                }
            }

        }
    )
}
