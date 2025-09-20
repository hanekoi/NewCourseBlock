package org.hanekoi.newcourseblock.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.hanekoi.newcourseblock.R
import org.hanekoi.newcourseblock.ui.viewmodel.DayUiState
import org.hanekoi.newcourseblock.utils.getFormattedDate

/**
 * App顶栏
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayAndWeekTopBar(
    dayUiState: DayUiState,
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
                    text =
                        // TODO: 中文适配
                        "${dayUiState.todayDate.getFormattedDate()} | Week ${dayUiState.currentWeek}"
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(
    title: String,
    onClick: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = Icons.AutoMirrored.Default.ArrowBack.name
                )
            }
        },
    )
}
