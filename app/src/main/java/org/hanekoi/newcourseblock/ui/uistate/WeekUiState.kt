package org.hanekoi.newcourseblock.ui.uistate

import org.hanekoi.newcourseblock.data.Course
import java.time.LocalDate

data class WeekUiState(
    val courses: List<Course>,
    val columns: Int,
    val rows: Int,
    val currentWeek: Int,
    val todayDate: LocalDate,
    val weekDates: List<LocalDate>
)