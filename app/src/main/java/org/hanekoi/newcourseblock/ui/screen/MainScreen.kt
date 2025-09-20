package org.hanekoi.newcourseblock.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.hanekoi.newcourseblock.data.Course
import org.hanekoi.newcourseblock.data.SecondaryScreenType
import org.hanekoi.newcourseblock.ui.component.DayAndWeekTopBar
import org.hanekoi.newcourseblock.ui.component.MainScreenBottomBar
import org.hanekoi.newcourseblock.ui.component.MeTopBar
import org.hanekoi.newcourseblock.ui.component.CourseDetailScreen
import org.hanekoi.newcourseblock.ui.viewmodel.DayViewModel
import org.hanekoi.newcourseblock.ui.viewmodel.MeViewModel
import org.hanekoi.newcourseblock.ui.viewmodel.WeekViewModel

@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var currentScreen by rememberSaveable { mutableStateOf(SecondaryScreenType.Week) } // 持久储存状态

    var selectedCourse by remember { mutableStateOf<Course?>(null) } // 记录目前正被长按选中的卡片
    val onLongPress: (Course) -> Unit = { it -> selectedCourse = it }

    val weekViewModel: WeekViewModel = viewModel<WeekViewModel>()
    val weekUiState by weekViewModel.uiState.collectAsState()
    val dayViewModel: DayViewModel = viewModel<DayViewModel>()
    val dayUiState by dayViewModel.uiState.collectAsState()
    val meViewModel: MeViewModel = viewModel<MeViewModel>()
    val meUiState by meViewModel.uiState.collectAsState()

    selectedCourse?.let {
        CourseDetailScreen(
            course = it,
            onDismissRequest = { selectedCourse = null }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            // 周和日视图下显示日期
            when(currentScreen) {
                SecondaryScreenType.Day -> DayAndWeekTopBar(dayUiState)
                SecondaryScreenType.Week -> DayAndWeekTopBar(dayUiState)
                SecondaryScreenType.Me -> MeTopBar()
            }
        },
        bottomBar = {
            MainScreenBottomBar(
                currentScreen = currentScreen,
                onClick = { it -> currentScreen = it },
            )
        }
    ) { innerPadding ->
        AnimatedContent( // 添加简易动效
            targetState = currentScreen,
            transitionSpec = {
                fadeIn() togetherWith (fadeOut())
            }
        ) { screen ->
            when(screen) {
                SecondaryScreenType.Day -> DayScreen(
                    uiState = dayUiState,
                    onLongPress = onLongPress,
                    modifier = Modifier.padding(innerPadding)
                )
                SecondaryScreenType.Week -> WeekScreen(
                    uiState = weekUiState,
                    onLongPress = onLongPress,
                    modifier = Modifier.padding(innerPadding)
                )
                SecondaryScreenType.Me -> MeScreen(
                    uiState = meUiState,
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
