package org.hanekoi.newcourseblock.data

// TODO: Course 类整体实现有问题, 需修改
data class CourseTime(
    val day: Int, // 星期几
    val start: Int, // 起始节次
    val end: Int, // 结束节次
    val weeks: List<Int>, // 上课周数
    val note: String = ""
)

data class Course(
    val name: String,
    val code: String,
    val location: String,
    val teacher: String,
    val times: List<CourseTime> // 所有上课时间段
)
