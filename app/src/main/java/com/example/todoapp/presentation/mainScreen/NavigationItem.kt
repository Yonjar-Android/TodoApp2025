package com.example.todoapp.presentation.mainScreen

import com.example.todoapp.R

enum class NavigationItem(
    val title: String,
    val icon: Int
) {
    Menu(
        title = "Menu",
        icon = R.drawable.menu
    ),
    Categories(
        title = "Categories",
        icon = R.drawable.category
    ),
    Analytics(
        title = "Analytics",
        icon = R.drawable.analytics
    )
}