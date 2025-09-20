package org.hanekoi.newcourseblock.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import org.hanekoi.newcourseblock.R
import org.hanekoi.newcourseblock.ui.navigation.NavDestination

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
