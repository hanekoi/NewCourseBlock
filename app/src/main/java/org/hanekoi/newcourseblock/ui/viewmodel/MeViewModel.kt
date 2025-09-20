package org.hanekoi.newcourseblock.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.hanekoi.newcourseblock.data.local.LocalCoursesDataProvider

data class MeUiState(
    val studentName: String,
    val studentId: String
)

class MeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        LocalCoursesDataProvider.defaultMeUiState
    )

    val uiState = _uiState.asStateFlow()
}