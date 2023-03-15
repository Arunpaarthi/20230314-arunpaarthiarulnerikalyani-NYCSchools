package com.arun.nycschools.viewmodel

import com.arun.nycschools.MainDispatcherRule
import com.arun.nycschools.model.data.SchoolMarks
import com.arun.nycschools.model.repository.FakeRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SatMarksViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SatMarksViewModel
    private lateinit var repo: FakeRepo

    @Before
    fun setUp() {
        repo = FakeRepo()
        viewModel = SatMarksViewModel(repo)
    }

    @Test
    fun getAverageSatMarks() = runTest {
        assertEquals(true, viewModel.schoolMarks.value.dbn == "")
        viewModel.getAverageSatMarks("123")
        repo.emitSchoolMarks(
            listOf(
                SchoolMarks(
                    dbn = "123",
                    num_of_sat_test_takers = "",
                    sat_critical_reading_avg_score = "",
                    sat_math_avg_score = "",
                    sat_writing_avg_score = "",
                    school_name = ""
                )
            )
        )
        assertEquals(true, viewModel.schoolMarks.value.dbn == "123")
        repo.emitSchoolMarks(
            listOf(
                SchoolMarks(
                    dbn = "345",
                    num_of_sat_test_takers = "",
                    sat_critical_reading_avg_score = "",
                    sat_math_avg_score = "",
                    sat_writing_avg_score = "",
                    school_name = ""
                ),
                SchoolMarks(
                    dbn = "123",
                    num_of_sat_test_takers = "",
                    sat_critical_reading_avg_score = "",
                    sat_math_avg_score = "",
                    sat_writing_avg_score = "",
                    school_name = ""
                )
            )
        )
        assertEquals(true, viewModel.schoolMarks.value.dbn == "345")
        repo.emitSchoolMarks(
            emptyList()
        )
        assertEquals(true, viewModel.uiState.first().isError)
    }
}