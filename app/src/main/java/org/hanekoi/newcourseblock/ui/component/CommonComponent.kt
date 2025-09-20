package org.hanekoi.newcourseblock.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.hanekoi.newcourseblock.data.Course
import org.hanekoi.newcourseblock.utils.getFormattedTime
import org.hanekoi.newcourseblock.utils.periods

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