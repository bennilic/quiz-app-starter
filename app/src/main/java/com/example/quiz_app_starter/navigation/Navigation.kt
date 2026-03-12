package com.example.quiz_app_starter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quiz_app_starter.MainMenuScreen
import com.example.quiz_app_starter.model.getDummyQuestions
import com.example.quiz_app_starter.presentation.FinishScreen
import com.example.quiz_app_starter.presentation.QuestionScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MainMenu.route
    ) {
        composable(Screen.MainMenu.route) {
            MainMenuScreen(
                bestScore = 42,
                onPlayClick = {
                    navController.navigate(Screen.Question.route)
                }
            )
        }

        composable(Screen.Question.route) {
            QuestionScreen(
                questions = getDummyQuestions(),
                onNavigateToFinish = { points ->
                    navController.navigate(Screen.Finish.createRoute(points)) {
                        popUpTo(Screen.Question.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.Finish.ROUTE,
            arguments = listOf(navArgument("points") { type = NavType.IntType })
        ) { backStackEntry ->
            val points = backStackEntry.arguments?.getInt("points") ?: 0
            FinishScreen(
                points = points,
                onRestart = {
                    navController.navigate(Screen.Question.route) {
                        popUpTo(Screen.Finish.ROUTE) { inclusive = true }
                    }
                },
                onHome = {
                    navController.navigate(Screen.MainMenu.route) {
                        popUpTo(Screen.MainMenu.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
