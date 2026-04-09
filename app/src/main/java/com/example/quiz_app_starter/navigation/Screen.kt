package com.example.quiz_app_starter.navigation

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object Question : Screen("question")
    object Finish : Screen("finish/{points}") {
        const val ROUTE = "finish/{points}"
        fun createRoute(points: Int) = "finish/$points"
    }
}
