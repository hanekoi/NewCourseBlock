package org.hanekoi.newcourseblock.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.hanekoi.newcourseblock.R
import org.hanekoi.newcourseblock.data.Course
import org.hanekoi.newcourseblock.data.local.LocalCoursesDataProvider
import org.hanekoi.newcourseblock.ui.uistate.WeekUiState
import javax.xml.validation.ValidatorHandler

@Composable
fun WeekScreen(
    uiState: WeekUiState,
    modifier: Modifier = Modifier
) {
    val screenHeight: Dp = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        WeekScreenTopBar(
            columns = uiState.columns
        )
        Row(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .height(screenHeight) // 将屏幕高度用作网格高度
        ) {
            WeekScreenSideBar(
                rows = uiState.rows
            )

            BoxWithConstraints(
                modifier = Modifier
            ) {
                WeekScreenBackground(
                    color = MaterialTheme.colorScheme.background
                )

                WeekScreenGrid(
                    columns = uiState.columns,
                    rows = uiState.rows,
                    width = maxWidth,
                    height = maxHeight
                )

                WeekScreenCourses(
                    courses = LocalCoursesDataProvider.testCourses,
                    columns = uiState.columns,
                    rows = uiState.rows,
                    width = maxWidth,
                    height = maxHeight,
                )
            }
        }
    }
}

/*
    日期顶栏实现
 */
@Composable
private fun WeekScreenTopBar(
    // TODO: uiState 传递当前日期
    columns: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .height(32.dp)
    ) {
        val days = stringArrayResource(R.array.week_days)

        Box( // TODO: 显示周数
            modifier = Modifier
                // TODO: 动态确定宽度
                .width(40.dp) // 固定宽度以和侧栏匹配
                .fillMaxHeight(),
        )
        // TODO: 不显示周末
        for(i in 0 until columns) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = days[i],
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

/*
    时间侧栏实现
 */
@Composable
private fun WeekScreenSideBar(
    rows: Int,
    modifier: Modifier = Modifier
) {
    val times = stringArrayResource(R.array.course_times)
    Column(
        modifier = modifier
            .width(40.dp)
    ) {
        for(i in 0 until rows) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Text(
                    text = (i+1).toString(),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = times[i*2],
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = times[i*2+1],
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

/*
    背景颜色实现
 */
@Composable
private fun WeekScreenBackground(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color)
    )
}

/*
    课表网格实现
 */
@Composable
private fun WeekScreenGrid(
    columns: Int,
    rows:Int,
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier
) {
    val cellWidth = width / columns
    val cellHeight = height / rows
    val color = Color.Gray.copy(alpha = 0.5f)

    Canvas(
        modifier = modifier
    ) {
        val w = width.toPx()
        val h = height.toPx()
        val cw = cellWidth.toPx()
        val ch = cellHeight.toPx()
        val strokeWidth = 1.dp.toPx()

        // 绘制垂直直线
        for (col in 0..columns-1) {
            val x = cw * col
            drawLine(
                color = color,
                start = Offset(x, 0f),
                end = Offset(x, h),
                strokeWidth = strokeWidth
            )
        }

        // 绘制水平直线
        for (row in 0..rows-1) {
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

/*
    课表课程实现
 */

@Composable
private fun WeekScreenCourseCard(
    name: String,
    location: String,
    modifier: Modifier = Modifier // 位置从 modifier 传递
) {
    Card(
        modifier = modifier.padding(2.dp),
        colors = CardDefaults.cardColors(containerColor = colorFromCourseName(name))
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "@$location",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

/*
    为每个卡片生成独特的颜色
 */
private fun colorFromCourseName(name: String): Color {
    val hash = name.hashCode()
    val r = (hash shr 16 and 0xFF)
    val g = (hash shr 8 and 0xFF)
    val b = (hash and 0xFF)
    return Color(r / 2 + 64, g / 2 + 64, b / 2 + 64, 0xFF)
}

@Composable
private fun WeekScreenCourses(
    courses: List<Course>,
    width: Dp,
    height: Dp,
    columns: Int,
    rows: Int,
    modifier: Modifier = Modifier
) {
    val cellWidth = width / columns
    val cellHeight = height / rows
    var selectedCourse by remember { mutableStateOf<Course?>(null) } // 记录目前正被长按选中的卡片

    selectedCourse?.let {
        WeekScreenCourseDetail(
            course = it,
            onDismissRequest = { selectedCourse = null }
        )
    }

    Box(
        modifier = modifier
    ) {
        // 创建课程卡片
        courses.forEach { course ->
            course.times.forEach { time ->
                val offsetX = cellWidth * (time.day - 1)
                val offsetY = cellHeight * (time.start - 1)
                val duration = time.end - time.start + 1

                WeekScreenCourseCard(
                    name = course.name,
                    location = course.location,
                    modifier = Modifier
                        .offset(offsetX, offsetY)
                        .width(cellWidth)
                        .height(cellHeight * duration)
                        .pointerInput(Unit) { // 监测长按
                            detectTapGestures (
                                onLongPress = {
                                    selectedCourse = course
                                }
                            )
                        }
                )
            }
        }
    }
}

/*
    课程详细信息弹窗
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeekScreenCourseDetail(
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
