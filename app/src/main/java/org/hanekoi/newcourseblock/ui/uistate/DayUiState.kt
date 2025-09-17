package org.hanekoi.newcourseblock.ui.uistate

import org.hanekoi.newcourseblock.data.Course

data class DayUiState(
    val courses: List<Course>,

    val currentWeek: Int,
    val currentDay: Int,
)
