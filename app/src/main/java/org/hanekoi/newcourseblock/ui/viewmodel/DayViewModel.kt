package org.hanekoi.newcourseblock.ui.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.hanekoi.newcourseblock.data.local.LocalCoursesDataProvider
import org.hanekoi.newcourseblock.ui.uistate.DayUiState
import java.time.LocalDate

class DayViewModel {
    val today: LocalDate = LocalDate.now()

    private val _uiState = MutableStateFlow( // 用于修改
        LocalCoursesDataProvider.defaultDayUiState
    )
    val uiState: StateFlow<DayUiState> = _uiState.asStateFlow() // 用于访问
}