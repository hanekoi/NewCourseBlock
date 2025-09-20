package org.hanekoi.newcourseblock.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.hanekoi.newcourseblock.R

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .animateContentSize()
            .fillMaxWidth(),
    ) {
        item {
            Text("Course")
            HorizontalDivider()

            SettingScreenClickableSurface(
                text = stringResource(R.string.current_academic_year),
                description = "2024-2045",
                onClick = {},
            )

            HorizontalDivider()

            SettingScreenClickableSurface(
                text = stringResource(R.string.current_term),
                description = "2",
                onClick = {},
            )

            HorizontalDivider()

            SettingScreenClickableSurface(
                text = stringResource(R.string.first_day_of_the_term),
                description = "2025-3-1",
                onClick = {},
            )

            HorizontalDivider()

            Spacer(modifier.height(40.dp))

            Text("Display")
            HorizontalDivider()

            SettingScreenSwitchSurface(
                text = stringResource(R.string.show_weekend)
            )

            HorizontalDivider()

        }
    }
}

@Composable
fun SettingScreenClickableSurface(
    text: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
    ) {
        Column(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = description,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }

}

@Composable
fun SettingScreenSwitchSurface(
    text: String,
    modifier: Modifier = Modifier
    ) {
    var switchState by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
            Switch(
                checked = switchState,
                onCheckedChange = { checked ->
                    switchState = checked
                }
            )
        }
    }
}
