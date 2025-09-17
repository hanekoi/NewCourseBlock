package org.hanekoi.newcourseblock.data.local

import org.hanekoi.newcourseblock.data.Course
import org.hanekoi.newcourseblock.data.CourseTime
import org.hanekoi.newcourseblock.ui.uistate.WeekUiState

object LocalCoursesDataProvider {


    val testCourses: List<Course> = listOf(
        Course(
            name = "高等数学",
            code = "MATH101",
            location = "教学楼A101",
            teacher = "王老师",
            times = listOf(
                CourseTime(day = 1, start = 1, end = 2, weeks = (1..16).toList()),
                CourseTime(day = 3, start = 3, end = 4, weeks = (1..16).toList())
            )
        ),
        Course(
            name = "大学英语",
            code = "ENG102",
            location = "教学楼B202",
            teacher = "李老师",
            times = listOf(
                CourseTime(day = 2, start = 5, end = 6, weeks = (1..8).toList()),
                CourseTime(day = 4, start = 1, end = 2, weeks = (1..8).toList())
            )
        ),
        Course(
            name = "计算机基础",
            code = "CS103",
            location = "机房C303",
            teacher = "张老师",
            times = listOf(
                CourseTime(day = 5, start = 7, end = 8, weeks = (9..16).toList())
            )
        ),
        Course(
            name = "体育",
            code = "PE104",
            location = "操场",
            teacher = "赵老师",
            times = listOf(
                CourseTime(day = 3, start = 5, end = 6, weeks = (1..16).toList(), note = "需穿运动服")
            )
        ),
        Course(
            name = "物理实验",
            code = "PHY105",
            location = "实验楼D404",
            teacher = "钱老师",
            times = listOf(
                CourseTime(day = 2, start = 3, end = 5, weeks = listOf(2,4,6,8,10,12,14,16), note = "双周上课")
            )
        ),
        Course(
            name = "马克思主义原理",
            code = "POL106",
            location = "教学楼E505",
            teacher = "孙老师",
            times = listOf(
                CourseTime(day = 1, start = 7, end = 8, weeks = (1..16).toList()),
                CourseTime(day = 5, start = 1, end = 2, weeks = (1..16).toList())
            )
        )
    )

    val defaultWeekUiState = WeekUiState(
        courses = testCourses,
        columns = 7,
        rows = 14,
        currentWeek = 3,
        currentDay = 4
    )
}