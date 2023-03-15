package com.arun.nycschools.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arun.nycschools.model.UiState
import com.arun.nycschools.model.data.SchoolMarks
import com.arun.nycschools.model.repository.NYCSchoolRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 *  [SatMarksViewModel] is responsible for interacting with repository and providing data to
 *  [SchoolDetailFragment][com.arun.nycschools.view.schooldetail.SchoolDetailFragment]
 *  @param repo Repository Interface which will be bound at runtime
 * */
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

    /**
     * [getAverageSatMarks] method is responsible for obtaining SAT marks for a specific school
     * from backend and assigning it to the [_schoolMarks] flow
     *
     * @param dbn - DBN of the school to which we are obtaining the marks
     * */
    fun getAverageSatMarks(dbn: String) {
        if(schoolMarks.value.dbn == "") {
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
}