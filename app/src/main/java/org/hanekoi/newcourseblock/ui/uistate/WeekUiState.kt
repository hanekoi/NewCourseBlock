package org.hanekoi.newcourseblock.ui.uistate

import org.hanekoi.newcourseblock.data.Course

data class WeekUiState(
    val courses: List<Course>,
    val columns: Int,
    val rows: Int,
    val currentWeek: Int,
    val currentDay: Int,
)