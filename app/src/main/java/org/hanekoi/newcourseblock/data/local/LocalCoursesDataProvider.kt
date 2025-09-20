package org.hanekoi.newcourseblock.data.local

import org.hanekoi.newcourseblock.data.Course
import org.hanekoi.newcourseblock.data.CourseTime
import org.hanekoi.newcourseblock.ui.viewmodel.DayUiState
import org.hanekoi.newcourseblock.ui.viewmodel.WeekUiState
import org.hanekoi.newcourseblock.utils.getTodayDate
import org.hanekoi.newcourseblock.utils.getWeekDates

object LocalCoursesDataProvider {
    val testCourses = listOf(
        // 1. 单周，周一，第一节到第二节
        Course(
            name = "高等数学",
            code = "MATH101",
            location = "教学楼A101",
            teacher = "张老师",
            times = listOf(
                CourseTime(day = 1, start = 1, end = 2, weeks = listOf(1,3,5,7,9,11,13,15))
            ),
            note = "单周课程"
        ),
        // 2. 双周，周二，第三节到第四节
        Course(
            name = "大学英语",
            code = "ENG102",
            location = "教学楼B202",
            teacher = "李老师",
            times = listOf(
                CourseTime(day = 2, start = 3, end = 4, weeks = listOf(2,4,6,8,10,12,14,16))
            ),
            note = "双周课程"
        ),
        // 3. 每周，周三，上午
        Course(
            name = "物理实验",
            code = "PHY103",
            location = "实验楼C303",
            teacher = "王老师",
            times = listOf(
                CourseTime(day = 3, start = 1, end = 4, weeks = (1..16).toList())
            ),
            note = "每周课程"
        ),
        // 4. 周末，周六，下午
        Course(
            name = "编程基础",
            code = "CS104",
            location = "机房D404",
            teacher = "赵老师",
            times = listOf(
                CourseTime(day = 6, start = 4, end = 5, weeks = (1..16).toList())
            ),
            note = "周六课程"
        ),
        // 5. 周末，周日，晚上
        Course(
            name = "美术鉴赏",
            code = "ART105",
            location = "艺术楼E505",
            teacher = "钱老师",
            times = listOf(
                CourseTime(day = 7, start = 9, end = 10, weeks = listOf(1,2,3,4,5,6,7,8))
            ),
            note = "周日课程"
        ),
        // 6. 单周，周五，全天
        Course(
            name = "体育",
            code = "PE106",
            location = "体育馆F606",
            teacher = "孙老师",
            times = listOf(
                CourseTime(day = 5, start = 1, end = 10, weeks = listOf(1,3,5,7,9,11,13,15))
            ),
            note = "单周全天"
        ),
        // 7. 双周，周三，早晚各一节
        Course(
            name = "化学",
            code = "CHEM107",
            location = "化学楼G707",
            teacher = "周老师",
            times = listOf(
                CourseTime(day = 3, start = 9, end = 10, weeks = listOf(2,4,6,8,10,12,14,16))
            ),
            note = "双周早晚"
        ),
        // 8. 每周，周四，单节课
        Course(
            name = "心理健康",
            code = "PSY108",
            location = "心理楼H808",
            teacher = "吴老师",
            times = listOf(
                CourseTime(day = 4, start = 5, end = 5, weeks = (1..16).toList())
            ),
            note = "每周单节"
        ),
        // 9. 单周，周一，跨周时间段
        Course(
            name = "历史",
            code = "HIS109",
            location = "历史楼I909",
            teacher = "郑老师",
            times = listOf(
                CourseTime(day = 1, start = 7, end = 10, weeks = listOf(1,3,5,7,9,11,13,15))
            ),
            note = "单周跨节"
        ),
        // 10. 双周，周五，上午
        Course(
            name = "地理",
            code = "GEO110",
            location = "地理楼J010",
            teacher = "冯老师",
            times = listOf(
                CourseTime(day = 5, start = 1, end = 4, weeks = listOf(2,4,6,8,10,12,14,16))
            ),
            note = "双周上午"
        )
    )

    val defaultWeekUiState = WeekUiState(
        courses = testCourses,
        columns = 7,
        rows = 14,
        currentWeek = 10,
        todayDate = getTodayDate(),
        weekDates = getWeekDates()
    )

    val defaultDayUiState = DayUiState(
        courses = testCourses,
        rows = 14,
        currentWeek = 10,
        todayDate = getTodayDate()
    )
}