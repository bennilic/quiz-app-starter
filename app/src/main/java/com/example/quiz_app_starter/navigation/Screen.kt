package com.example.quiz_app_starter.navigation

sealed class Screen(val route: String) {
    data object MainMenu : Screen("main_menu")
    data object Question : Screen("question")
    data class Finish(val points: Int) : Screen("finish/{points}") {
        companion object {
            const val ROUTE = "finish/{points}"
            fun createRoute(points: Int) = "finish/$points"
        }
    }
}
