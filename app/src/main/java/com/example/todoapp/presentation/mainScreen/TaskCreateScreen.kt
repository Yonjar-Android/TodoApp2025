package com.example.todoapp.presentation.mainScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.todoapp.R
import com.example.todoapp.data.models.CategoryWithTaskCount
import com.example.todoapp.data.models.TaskModel
import com.example.todoapp.ui.theme.BlueBgTwo
import com.example.todoapp.utils.GetStringObject

@Composable
fun TaskCreateScreen(
    categories: List<CategoryWithTaskCount>,
    viewModel: MainScreenViewModel,
    onDismiss: () -> Unit,
    contexts: Context
) {
    var taskName by remember { mutableStateOf("") }
    var taskCategory by remember { mutableStateOf("") }
    var categoryId by remember { mutableLongStateOf(0L) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.testTag("screenCreateTask")
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.createTaskMsg),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = taskName,
                    onValueChange = { taskName = it },
                    label = { Text(stringResource(R.string.createTaskNameMsg)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("tfNameTask"),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = taskCategory,
                    onValueChange = { taskCategory = it },
                    label = { Text(stringResource(R.string.categorieStr)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    readOnly = true,
                    trailingIcon = {
                        CategoryDropDownMenu(
                            categories,
                            categorySelected = { categoryName, categoryIdd ->
                                taskCategory = categoryName
                                categoryId = categoryIdd
                            })
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = BlueBgTwo)
                    ) {
                        Text(stringResource(R.string.cancelStr))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(modifier = Modifier.testTag("btnCreateTask"), onClick = {
                        if (taskName.trim().isEmpty()) {
                            Toast.makeText(
                                contexts,
                                GetStringObject.getStringResource(contexts,R.string.createTaskMsg),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }
                        if (categoryId == 0L) {
                            Toast.makeText(
                                contexts,
                                GetStringObject.getStringResource(contexts,R.string.chooseCategoryMsg),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }

                        val task = TaskModel(
                            title = taskName,
                            categoryId = categoryId,
                            createdDate = System.currentTimeMillis(),
                        )
                        viewModel.createTask(task)

                        onDismiss()
                    }, colors = ButtonDefaults.buttonColors(containerColor = BlueBgTwo)) {
                        Text(stringResource(R.string.createStr))
                    }
                }
            }

        }
    }
}

@Composable
fun CategoryDropDownMenu(
    categories: List<CategoryWithTaskCount>,
    categorySelected: (String, Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        IconButton(
            modifier = Modifier.testTag("btnMenuIcon"),
            onClick = { expanded = !expanded }) {
            Icon(
                Icons.Default.ArrowDropDown, contentDescription = "More options",
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach {
                DropdownMenuItem(
                    modifier = Modifier.testTag("category${it.id}"),
                    text = { Text(it.title) },
                    onClick = {
                        expanded = !expanded
                        categorySelected(it.title, it.id)

                    }
                )
            }
        }
    }
}

