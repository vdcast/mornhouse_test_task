package com.vdcast.mornhousetesttask.ui.navigation

sealed class Routes(val route: String) {
    object Home : Routes("Home")
    object Details : Routes("Details")
}
