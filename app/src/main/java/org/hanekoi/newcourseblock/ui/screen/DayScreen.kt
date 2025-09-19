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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.hanekoi.newcourseblock.data.Course
import org.hanekoi.newcourseblock.ui.component.ScreenBackground
import org.hanekoi.newcourseblock.ui.theme.Shape
import org.hanekoi.newcourseblock.ui.theme.courseCardColor
import org.hanekoi.newcourseblock.ui.viewmodel.DayUiState
import org.hanekoi.newcourseblock.utils.getFormattedTime
import org.hanekoi.newcourseblock.utils.getTodayDate
import org.hanekoi.newcourseblock.utils.periods

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
                rows = uiState.rows,
                width = maxWidth,
                height = maxHeight,
            )

            DayScreenCourses(
                courses = uiState.courses,
                height = maxHeight,
                rows = uiState.rows,
                currentWeek = uiState.currentWeek,
                onLongPress = onLongPress
            )
        }
    }
}

@Composable
private fun DayScreenSideBar(
    rows: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(80.dp)
    ) {
        for(i in 0 until rows) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (i + 1).toString(),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(40.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = periods[i].first.getFormattedTime(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                    )
                    Text(
                        text = periods[i].second.getFormattedTime(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
private fun DayScreenGrid(
    rows:Int,
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier
) {
    val cellHeight = height / rows
    val color = Color.Gray.copy(alpha = 0.5f)

    Canvas(
        modifier = modifier
    ) {
        val w = width.toPx()
        val ch = cellHeight.toPx()
        val strokeWidth = 1.dp.toPx()

        // 绘制水平直线
        for (row in 0..rows) {
            val y = ch * row
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
    name: String,
    location: String,
    modifier: Modifier = Modifier // 位置从 modifier 传递
) {
    // TODO: 布局需要改进
    Card(
        modifier = modifier
            .padding(4.dp)
            .clip(Shape.medium),
        colors = CardDefaults.cardColors(containerColor = courseCardColor(name))
    ){
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = "@$location",
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
    rows: Int,
    currentWeek: Int,
    onLongPress: (Course) -> Unit,
    modifier: Modifier = Modifier
) {
    val cellHeight = height / rows

    Box(
        modifier = modifier
    ) {
        courses.forEach { course ->
            course.times.forEach { time ->
                if(getTodayDate().dayOfWeek.value == time.day
                    && currentWeek in time.weeks) {
                    val offsetY = cellHeight * (time.start - 1)
                    val duration = time.end - time.start + 1

                    DayScreenCourseCard(
                        name = course.name,
                        location = course.location,
                        modifier = Modifier
                            .offset(0.dp, offsetY)
                            .fillMaxWidth()
                            .height(cellHeight * duration)
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
