package org.hanekoi.newcourseblock.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.hanekoi.newcourseblock.data.local.LocalCoursesDataProvider
import org.hanekoi.newcourseblock.ui.uistate.WeekUiState
import java.time.DayOfWeek
import java.time.LocalDate

class WeekViewModel: ViewModel() {
    val today: LocalDate = LocalDate.now()
    val weekDates: List<LocalDate> = run {
        val monday = today.with(DayOfWeek.MONDAY)
        (0..6).map { monday.plusDays(it.toLong()) }
    }

    private val _uiState = MutableStateFlow( // 用于修改
        LocalCoursesDataProvider.defaultWeekUiState
    )
    val uiState: StateFlow<WeekUiState> = _uiState.asStateFlow() // 用于访问
}