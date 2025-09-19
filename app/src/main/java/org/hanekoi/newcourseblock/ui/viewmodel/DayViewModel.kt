package org.hanekoi.newcourseblock.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.hanekoi.newcourseblock.data.local.LocalCoursesDataProvider
import java.time.LocalDate
import org.hanekoi.newcourseblock.data.Course

data class DayUiState(
    val courses: List<Course>,
    val rows: Int,
    val currentWeek: Int,
    val todayDate: LocalDate
)

class DayViewModel: ViewModel() {
    private val _uiState = MutableStateFlow( // 用于修改
        LocalCoursesDataProvider.defaultDayUiState
    )
    val uiState: StateFlow<DayUiState> = _uiState.asStateFlow() // 用于访问
}