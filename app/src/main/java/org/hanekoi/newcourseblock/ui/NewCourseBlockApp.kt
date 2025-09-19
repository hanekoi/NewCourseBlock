package org.hanekoi.newcourseblock.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.hanekoi.newcourseblock.R
import org.hanekoi.newcourseblock.ui.navigation.NavDestination
import org.hanekoi.newcourseblock.ui.screen.DayScreen
import org.hanekoi.newcourseblock.ui.screen.MeScreen
import org.hanekoi.newcourseblock.ui.screen.WeekScreen
import org.hanekoi.newcourseblock.ui.theme.NewCourseBlockTheme
import org.hanekoi.newcourseblock.ui.viewmodel.WeekViewModel

@Composable
fun NewCourseBlockApp(
    modifier: Modifier = Modifier
) {
    val weekViewModel: WeekViewModel = viewModel()
    val weekUiState by weekViewModel.uiState.collectAsState()

    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            NewCourseBlockAppTopBar()
        },
        bottomBar = {
            NewCourseBlockAppBottomBar(
                navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavDestination.Week.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = NavDestination.Day.name) {
                DayScreen()
            }

            composable(route = NavDestination.Week.name) {
                WeekScreen(
                    uiState = weekUiState,
                    modifier = Modifier // 这里不需要再次传递 innerPadding
                )
            }

            composable(route = NavDestination.Me.name) {
                MeScreen()
            }
        }
    }
}

/**
    * App顶栏
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewCourseBlockAppTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                modifier = modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.app_name)
                )
                IconButton( // 添加新课程按钮
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = Icons.Filled.Add.name
                    )
                }
            }
        },
    )
}

/**
    * App底部导航栏
 */
private data class NavigationItemContent(
    val icon: ImageVector,
    val text: String,
    val route: String
)

@Composable
private fun navigationItemContentList() = listOf(
    // 日视图
    NavigationItemContent(
        icon = Icons.Filled.Check,
        text = stringResource(R.string.day),
        route = NavDestination.Day.name
    ),

    // 周视图
    NavigationItemContent(
        icon = Icons.Filled.DateRange,
        text = stringResource(R.string.week),
        route = NavDestination.Week.name
    ),

    // 我的
    NavigationItemContent(
        icon = Icons.Filled.AccountCircle,
        text = stringResource(R.string.me),
        route = NavDestination.Me.name
    )
)

@Composable
private fun NewCourseBlockAppBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route // 获取当前 route

    NavigationBar(
        modifier = modifier
    ) {
        for(navItem in navigationItemContentList()) {
            NavigationBarItem(
                selected = navItem.route == currentRoute, // 判断选中状态
                onClick = {
                    // 原理是?
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true // 避免重复堆栈
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                },
                label = {
                    Text(
                        text = navItem.text
                    )
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun NewCourseBlockAppPreview() {
    NewCourseBlockTheme {
        NewCourseBlockApp()
    }
}

