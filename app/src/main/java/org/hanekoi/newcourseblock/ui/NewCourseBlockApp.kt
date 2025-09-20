package org.hanekoi.newcourseblock.ui

import android.transition.Fade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.hanekoi.newcourseblock.data.Course
import org.hanekoi.newcourseblock.ui.component.BackTopBar
import org.hanekoi.newcourseblock.ui.component.DayAndWeekTopBar
import org.hanekoi.newcourseblock.ui.component.MeTopBar
import org.hanekoi.newcourseblock.ui.component.ScreenCourseDetail
import org.hanekoi.newcourseblock.ui.navigation.NavDestination
import org.hanekoi.newcourseblock.ui.screen.DayScreen
import org.hanekoi.newcourseblock.ui.screen.MeScreen
import org.hanekoi.newcourseblock.ui.screen.SettingsScreen
import org.hanekoi.newcourseblock.ui.screen.WeekScreen
import org.hanekoi.newcourseblock.ui.theme.NewCourseBlockTheme
import org.hanekoi.newcourseblock.ui.viewmodel.DayViewModel
import org.hanekoi.newcourseblock.ui.viewmodel.MeViewModel
import org.hanekoi.newcourseblock.ui.viewmodel.WeekViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun NewCourseBlockApp(
    modifier: Modifier = Modifier
) {
    val weekViewModel: WeekViewModel = viewModel<WeekViewModel>()
    val weekUiState by weekViewModel.uiState.collectAsState()
    val dayViewModel: DayViewModel = viewModel<DayViewModel>()
    val dayUiState by dayViewModel.uiState.collectAsState()
    val meViewModel: MeViewModel = viewModel<MeViewModel>()
    val meUiState by meViewModel.uiState.collectAsState()

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route // 获取当前 route

    var selectedCourse by remember { mutableStateOf<Course?>(null) } // 记录目前正被长按选中的卡片
    val onLongPress: (Course) -> Unit = { it -> selectedCourse = it }

    selectedCourse?.let {
        ScreenCourseDetail(
            course = it,
            onDismissRequest = { selectedCourse = null }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            // 仅在周和日视图下显示 TopAppBar
            when(currentRoute) {
                NavDestination.Day.name -> DayAndWeekTopBar(dayUiState)
                NavDestination.Week.name -> DayAndWeekTopBar(dayUiState)
                NavDestination.Me.name -> MeTopBar()
                NavDestination.Settings.name -> BackTopBar(stringResource(R.string.settings),{ navController.navigateUp() })
            }
        },
        bottomBar = {
            if(currentRoute == NavDestination.Day.name
                || currentRoute == NavDestination.Week.name
                || currentRoute == NavDestination.Me.name) {
                NewCourseBlockAppBottomBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavDestination.Week.name,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
                    modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = NavDestination.Day.name) {
                DayScreen(
                    uiState = dayUiState,
                    onLongPress = onLongPress,
                    modifier = Modifier
                )
            }

            composable(route = NavDestination.Week.name) {
                WeekScreen(
                    uiState = weekUiState,
                    onLongPress = onLongPress,
                    modifier = Modifier // 这里不需要再次传递 innerPadding
                )
            }

            composable(route = NavDestination.Me.name) {
                MeScreen(
                    uiState = meUiState,
                    navController = navController,
                    modifier = Modifier
                )
            }

            composable(route = NavDestination.Settings.name) {
                SettingsScreen()
            }
        }
    }
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
    currentRoute: String?,
    modifier: Modifier = Modifier
) {
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

