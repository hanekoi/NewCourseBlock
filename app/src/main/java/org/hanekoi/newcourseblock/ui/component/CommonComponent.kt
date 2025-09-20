package org.hanekoi.newcourseblock.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.hanekoi.newcourseblock.data.Course

/**
 * 课程详细信息半屏弹窗
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScreenCourseDetail(
    course: Course,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        // TODO: 布局有待改进
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = course.name,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = "@${course.location}",
                style = MaterialTheme.typography.headlineMedium,
            )
            HorizontalDivider(
                modifier = Modifier.padding(8.dp),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )
            Text(
                text = course.code
            )
            Text(
                text = course.teacher
            )
        }
    }
}

/**
 * 课表绘制底层 颜色
 */
@Composable
internal fun ScreenBackground(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color)
    )
}

@Composable
fun ClickableCard(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    onClick:() -> Unit = {},
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(top = 20.dp, bottom = 20.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = Icons.Default.Settings.name,
                modifier = Modifier
                    .size(36.dp)
                    .weight(1f)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Justify
            )
        }
    }
}
