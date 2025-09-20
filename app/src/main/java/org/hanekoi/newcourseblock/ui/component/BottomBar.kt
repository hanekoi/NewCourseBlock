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
import org.hanekoi.newcourseblock.R
import org.hanekoi.newcourseblock.data.SecondaryScreenType

/**
 * App底部导航栏
 */
private data class BarItemContent(
    val icon: ImageVector,
    val text: String,
    val type: SecondaryScreenType
)

@Composable
private fun barItemContentList() = listOf(
    // 日视图
    BarItemContent(
        icon = Icons.Filled.Check,
        text = stringResource(R.string.day),
        type = SecondaryScreenType.Day
    ),

    // 周视图
    BarItemContent(
        icon = Icons.Filled.DateRange,
        text = stringResource(R.string.week),
        type = SecondaryScreenType.Week
    ),

    // 我的
    BarItemContent(
        icon = Icons.Filled.AccountCircle,
        text = stringResource(R.string.me),
        type = SecondaryScreenType.Me
    )
)

@Composable
internal fun MainScreenBottomBar(
    currentScreen: SecondaryScreenType,
    onClick: (SecondaryScreenType) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        for(barItem in barItemContentList()) {
            NavigationBarItem(
                selected = barItem.type == currentScreen, // 判断选中状态
                onClick = { onClick(barItem.type) }, // 触发重组
                icon = {
                    Icon(
                        imageVector = barItem.icon,
                        contentDescription = barItem.text
                    )
                },
                label = {
                    Text(
                        text = barItem.text
                    )
                }
            )
        }
    }
}