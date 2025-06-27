package com.github.umangtapania.mytasks.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.umangtapania.mytasks.MyApp
import com.github.umangtapania.mytasks.utils.Constants
import com.github.umangtapania.mytasks.view.add_task_screen.AddOrEditTaskScreen
import com.github.umangtapania.mytasks.view.home_screen.HomeScreen
import com.github.umangtapania.mytasks.view.login_screen.LoginScreen
import com.github.umangtapania.mytasks.viewmodel.HomeViewModel
import com.github.umangtapania.mytasks.viewmodel.LoginViewModel
import com.github.umangtapania.mytasks.viewmodel.TaskFormViewModel

@Composable
fun MyTasksApp() {
    val navController = rememberNavController()

    val isLogin = MyApp.prefs.isLoginOnce()

    val startDestination = if (isLogin) Constants.HOME_SCREEN else Constants.LOGIN_SCREEN

    NavHost(navController, startDestination = startDestination) {
        composable(Constants.LOGIN_SCREEN) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(viewModel, navController)
        }
        composable(Constants.HOME_SCREEN) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(viewModel, navController)
        }
        composable(route = Constants.EDIT_TASK_SCREEN,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            val viewModel: TaskFormViewModel = hiltViewModel()
            AddOrEditTaskScreen(taskId, viewModel, navController)
        }
    }
}