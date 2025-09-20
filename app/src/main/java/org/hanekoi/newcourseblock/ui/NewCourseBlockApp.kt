package org.hanekoi.newcourseblock.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.hanekoi.newcourseblock.data.MainScreenType
import org.hanekoi.newcourseblock.ui.screen.MainScreen
import org.hanekoi.newcourseblock.ui.screen.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCourseBlockApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    // val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route // 获取当前 route

    NavHost(
        navController = navController,
        startDestination = MainScreenType.Main.name,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(MainScreenType.Main.name) {
            MainScreen(
                navController
            )
        }

        composable(MainScreenType.Setting.name) {
            SettingsScreen(
                onBackButtonClicked = { navController.navigateUp() }
            )
        }
    }
}



