package org.hanekoi.newcourseblock.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.hanekoi.newcourseblock.data.Course
import org.hanekoi.newcourseblock.data.CourseTime
import org.hanekoi.newcourseblock.data.local.LocalCoursesDataProvider
import org.hanekoi.newcourseblock.ui.component.ScreenBackground
import org.hanekoi.newcourseblock.ui.theme.NewCourseBlockTheme
import org.hanekoi.newcourseblock.ui.theme.Shape
import org.hanekoi.newcourseblock.ui.theme.courseCardColor
import org.hanekoi.newcourseblock.ui.viewmodel.DayUiState
import org.hanekoi.newcourseblock.utils.getFormattedTime
import org.hanekoi.newcourseblock.utils.getTodayDate
import org.hanekoi.newcourseblock.utils.periods
import java.time.LocalTime
import java.time.temporal.ChronoUnit

const val DAY_SCREEN_ROWS: Int = 15
val START_TIME: LocalTime = LocalTime.of(8, 0)
val END_TIME: LocalTime = LocalTime.of(22, 0)

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun DayScreen(
    uiState: DayUiState,
    onLongPress: (Course) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
    ) {
        DayScreenSideBar(
            uiState.rows
        )

        BoxWithConstraints {
            ScreenBackground(
                color = MaterialTheme.colorScheme.background
            )

            DayScreenGrid(
                width = maxWidth,
                height = maxHeight,
            )

            DayScreenCourses(
                courses = uiState.courses,
                height = maxHeight,
                currentWeek = uiState.currentWeek,
                onLongPress = onLongPress
            )
        }
    }
}

/**
 * 日视图侧边时间栏
 */
@Composable
private fun DayScreenSideBar(
    rows: Int,
    modifier: Modifier = Modifier
) {
    val hours = (8..22).toList()

    Column(
        modifier = modifier
            .width(60.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly, // 均匀分布
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        hours.forEach { hour ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "%02d:00".format(hour),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                )
            }
        }
    }

}

/**
 * 日视图时间网格
 */
@Composable
private fun DayScreenGrid(
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier
) {
    val cellHeight = height / DAY_SCREEN_ROWS
    val color = Color.Gray.copy(alpha = 0.5f)

    Canvas(
        modifier = modifier
    ) {
        val w = width.toPx()
        val ch = cellHeight.toPx()
        val strokeWidth = 1.dp.toPx()

        // 绘制水平直线
        for (row in 0..DAY_SCREEN_ROWS-1) { // 强制绘制
            val y = ch * (row + 0.5f)
            drawLine(
                color = color,
                start = Offset(0f, y),
                end = Offset(w, y),
                strokeWidth = strokeWidth
            )
        }
    }
}

@Composable
private fun DayScreenCourseCard(
    course: Course,
    time: CourseTime,
    modifier: Modifier = Modifier // 位置从 modifier 传递
) {
    // TODO: 布局需要改进
    Card(
        modifier = modifier
            .padding(start = 4.dp, end = 4.dp)
            .clip(Shape.medium),
        colors = CardDefaults.cardColors(containerColor = courseCardColor(course.name))
    ){
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = course.name,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    text =
                        periods[time.start-1].first.getFormattedTime()
                        + " - "
                        + periods[time.end-1].second.getFormattedTime()
                )
            }
            Text(
                text = "@${course.location}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DayScreenCourses(
    courses: List<Course>,
    height: Dp,
    currentWeek: Int,
    onLongPress: (Course) -> Unit,
    modifier: Modifier = Modifier
) {
    val cellHeight = height / DAY_SCREEN_ROWS
    val totalMinutes = START_TIME.until(END_TIME, ChronoUnit.MINUTES)

    Box(
        modifier = modifier
    ) {
        courses.forEach { course ->
            course.times.forEach { time ->
                if(getTodayDate().dayOfWeek.value == time.day
                    && currentWeek in time.weeks) {

                    val courseStart = periods[time.start - 1].first
                    val courseEnd = periods[time.end - 1].second
                    val offsetMinutes = START_TIME.until(
                        courseStart, ChronoUnit.MINUTES
                    )
                    val durationMinutes = courseStart.until(
                        courseEnd, ChronoUnit.MINUTES
                    )

                    // 由于上下空隙, 实际高度只有 (height - cellHeight), 在计算时也需要加上空隙
                    val offsetY =
                        (height - cellHeight) * (offsetMinutes.toFloat() / totalMinutes) + cellHeight / 2
                    val cardHeight = (height - cellHeight) * (durationMinutes.toFloat() / totalMinutes)

                    DayScreenCourseCard(
                        course = course,
                        time = time,
                        modifier = Modifier
                            .offset(0.dp, offsetY)
                            .fillMaxWidth()
                            .height(cardHeight)
                            .pointerInput(Unit) { // 监测长按
                                detectTapGestures (
                                    onLongPress = {
                                        onLongPress(course)
                                    }
                                )
                            }
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun DayScreenPreview() {
    NewCourseBlockTheme {
        DayScreen(
            uiState = LocalCoursesDataProvider.defaultDayUiState,
            onLongPress = {},
        )
    }
}
