package org.hanekoi.newcourseblock.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.hanekoi.newcourseblock.R
import org.hanekoi.newcourseblock.ui.component.ClickableCard
import org.hanekoi.newcourseblock.ui.navigation.NavDestination
import org.hanekoi.newcourseblock.ui.viewmodel.MeUiState

@Composable
fun MeScreen(
    uiState: MeUiState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        MeScreenStudentInfoCard(
            studentName = uiState.studentName,
            studentId = uiState.studentId
        )

        ClickableCard(
            icon = Icons.Default.Refresh,
            text = stringResource(R.string.sync),
        )

        ClickableCard(
            icon = Icons.Default.Settings,
            text = stringResource(R.string.settings),
            onClick = { navController.navigate(NavDestination.Settings.name) }
        )

        ClickableCard(
            icon = Icons.Default.Info,
            text = stringResource(R.string.about)
        )
    }
}

@Composable
private fun MeScreenStudentInfoCard(
    studentName: String,
    studentId: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(bottom = 40.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = studentName,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = studentId
            )
        }
    }
}
