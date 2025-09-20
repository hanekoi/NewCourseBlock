package org.hanekoi.newcourseblock.ui.screen

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import org.hanekoi.newcourseblock.ui.component.ScreenBackground
import org.hanekoi.newcourseblock.ui.theme.Shape
import org.hanekoi.newcourseblock.ui.theme.courseCardColor
import org.hanekoi.newcourseblock.ui.viewmodel.WeekUiState
import org.hanekoi.newcourseblock.utils.getFormattedTime
import org.hanekoi.newcourseblock.utils.periods
import java.time.LocalDate

/**
 * 周视图布局
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun WeekScreen(
    uiState: WeekUiState,
    onLongPress: (Course) -> Unit,
    modifier: Modifier = Modifier
) {
    val screenHeight: Dp = LocalConfiguration.current.screenHeightDp.dp // 获取当前屏幕高度

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        WeekScreenTopBar(
            todayDate = uiState.todayDate,
            weekDates = uiState.weekDates,
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

            BoxWithConstraints( // 课表网格分层绘制
                modifier = Modifier
            ) {
                ScreenBackground(
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
                    currentWeek = uiState.currentWeek,
                    onLongPress = onLongPress,
                    height = maxHeight,
                )
            }
        }
    }
}

/**
    * 周视图日期顶栏
 */
@Composable
private fun WeekScreenTopBar(
    columns: Int,
    todayDate: LocalDate,
    weekDates: List<LocalDate>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .height(40.dp)
    ) {
        val days = stringArrayResource(R.array.week_days)

        Box(
            modifier = Modifier
                // TODO: 动态确定宽度
                .width(40.dp) // 固定宽度以和侧栏匹配
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ){}

        // TODO: 不显示周末
        for(i in 0 until columns) {
            val isToday = todayDate == weekDates[i] // 判断是否为今天以确定是否高亮
            val backgroundColor = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
            val textColor = if (isToday) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(Shape.medium)
                    .background(backgroundColor),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = days[i],
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor
                )
                Text(
                    text = "${weekDates[i].monthValue}/${weekDates[i].dayOfMonth}",
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor
                )
            }
        }
    }
}

/**
 * 视图时间侧栏
 */
@Composable
private fun WeekScreenSideBar(
    rows: Int,
    modifier: Modifier = Modifier
) {
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
                    text = periods[i].first.getFormattedTime(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = periods[i].second.getFormattedTime(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

/**
    * 课表绘制中层 网格
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
        for (col in 0..columns) {
            val x = cw * col
            drawLine(
                color = color,
                start = Offset(x, 0f),
                end = Offset(x, h),
                strokeWidth = strokeWidth
            )
        }

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

/**
    * 课表绘制顶层 课程卡片
 */
@Composable
private fun WeekScreenCourseCard(
    name: String,
    location: String,
    modifier: Modifier = Modifier // 位置从 modifier 传递
) {
    // TODO: 背景颜色和字体颜色还需要调整
    Card(
        modifier = modifier
            .padding(2.dp)
            .clip(Shape.medium),
        colors = CardDefaults.cardColors(containerColor = courseCardColor(name))
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

/**
    * 课程卡片颜色生成
 */


@Composable
private fun WeekScreenCourses(
    courses: List<Course>,
    width: Dp,
    height: Dp,
    columns: Int,
    rows: Int,
    currentWeek: Int,
    onLongPress: (Course) -> Unit,
    modifier: Modifier = Modifier
) {
    val cellWidth = width / columns
    val cellHeight = height / rows

    Box(
        modifier = modifier
    ) {
        // TODO: 抽取当周课程判断逻辑
        // 创建课程卡片
        courses.forEach { course ->
            course.times.forEach  { time ->
                if(currentWeek in time.weeks) {
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

