package com.arun.nycschools.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arun.nycschools.model.UiState
import com.arun.nycschools.model.data.School
import com.arun.nycschools.model.repository.NYCSchoolRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  [SchoolListViewModel] is responsible for interacting with repository and providing data to
 *  [NYCSchoolListFragment][com.arun.nycschools.view.schoollist.NYCSchoolListFragment]
 *  @param repo Repository Interface which will be bound at runtime
 * */
@HiltViewModel
class SchoolListViewModel @Inject constructor(private var repo: NYCSchoolRepo): ViewModel() {

    private var _uiState = MutableStateFlow(UiState(isLoading = false, isError = false))
    val uiState: StateFlow<UiState> = _uiState

    private var _schools = MutableStateFlow<List<School>>(emptyList())
    val schools: StateFlow<List<School>> = _schools

    /**
     * [getSchools] function is responsible for initiating the backend Api call to obtain
     * list of Schools
     * */
    fun getSchools() {
        if (schools.value.isEmpty()) {
            _uiState.value = UiState(
                isLoading = true,
                isError = false
            )
            viewModelScope.launch {
                repo.getSchools().collect {
                    _schools.value = it
                    if (schools.value.isNotEmpty()) {
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