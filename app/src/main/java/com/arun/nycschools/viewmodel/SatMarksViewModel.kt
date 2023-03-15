package com.arun.nycschools.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.arun.nycschools.model.UiState
import com.arun.nycschools.model.data.SchoolMarks
import com.arun.nycschools.model.network.NYCSchoolApiService
import com.arun.nycschools.model.repository.NYCSchoolRepo
import com.arun.nycschools.model.repository.NYCSchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatMarksViewModel @Inject constructor(private val repo: NYCSchoolRepo) : ViewModel() {

    private var _uiState = MutableStateFlow(UiState(isLoading = false, isError = false))
    val uiState: StateFlow<UiState> = _uiState

    private var _schoolMarks = MutableStateFlow(
        SchoolMarks(
            dbn = "",
            num_of_sat_test_takers = "",
            sat_critical_reading_avg_score = "",
            sat_math_avg_score = "",
            sat_writing_avg_score = "",
            school_name = ""
        )
    )
    val schoolMarks: StateFlow<SchoolMarks> = _schoolMarks

    fun getAverageSatMarks(dbn: String) {
        _uiState.value = UiState(
            isLoading = true,
            isError = false
        )
        viewModelScope.launch {
            repo.getSatMarks(dbn).collect {
                val list = it
                if (list.isNotEmpty()) {
                    _schoolMarks.value = list[0]
                    _uiState.value = UiState(
                        isLoading = false,
                        isError = false
                    )
                } else {
                    _uiState.value = UiState(
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }
}