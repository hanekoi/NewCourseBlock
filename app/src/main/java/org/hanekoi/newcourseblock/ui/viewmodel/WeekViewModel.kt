package org.hanekoi.newcourseblock.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.hanekoi.newcourseblock.data.local.LocalCoursesDataProvider
import org.hanekoi.newcourseblock.ui.uistate.WeekUiState

class WeekViewModel: ViewModel() {
    private val _uiState = MutableStateFlow( // 用于修改
        LocalCoursesDataProvider.defaultWeekUiState
    )
    val uiState: StateFlow<WeekUiState> = _uiState.asStateFlow() // 用于访问
}