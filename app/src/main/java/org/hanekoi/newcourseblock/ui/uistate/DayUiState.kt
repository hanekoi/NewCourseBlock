package org.hanekoi.newcourseblock.ui.uistate

import org.hanekoi.newcourseblock.data.Course
import java.time.LocalDate

data class DayUiState(
    val courses: List<Course>,
    val rows: Int,
    val currentWeek: Int,
    val today: LocalDate
)
