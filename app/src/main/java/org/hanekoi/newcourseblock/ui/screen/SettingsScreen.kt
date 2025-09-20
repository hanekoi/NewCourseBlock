package org.hanekoi.newcourseblock.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.hanekoi.newcourseblock.R
import org.hanekoi.newcourseblock.ui.component.ClickableCard

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            item {
                ClickableCard(
                    icon = Icons.Default.DateRange,
                    text = stringResource(R.string.current_academic_year)
                )
            }
    }
}

