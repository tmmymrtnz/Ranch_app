package com.example.myapplication

import androidx.compose.ui.graphics.painter.Painter

data class BotNavItem(
    val name: String,
    val route: String,
    val icon : Painter,
    val badgeCount: Int =0

)
