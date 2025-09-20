package org.hanekoi.newcourseblock.data

import org.hanekoi.newcourseblock.utils.periods

data class CourseTime(
    val day: Int, // 星期几
    val start: Int, // 起始节次
    val end: Int, // 结束节次
    val weeks: List<Int>, // 上课周数
)

data class Course(
    val name: String,
    val code: String,
    val location: String,
    val teacher: String,
    val times: List<CourseTime>, // 所有上课时间段
    val note: String
)
