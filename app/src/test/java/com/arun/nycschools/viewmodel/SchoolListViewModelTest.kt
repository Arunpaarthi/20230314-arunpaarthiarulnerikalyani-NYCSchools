package com.arun.nycschools.viewmodel

import com.arun.nycschools.MainDispatcherRule
import com.arun.nycschools.model.data.School
import com.arun.nycschools.model.repository.FakeRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SchoolListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SchoolListViewModel
    private lateinit var repo: FakeRepo

    @Before
    fun setUp() {
        repo = FakeRepo()
        viewModel = SchoolListViewModel(repo)
    }

    @Test
    fun getSchools() = runTest {

        assertEquals(0, viewModel.schools.value.size)
        viewModel.getSchools()
        repo.emitSchool(listOf(
            School(
                "123",
                "Fake School"
            )
        ))
        assertEquals(1, viewModel.schools.value.size)
        repo.emitSchool(listOf(
            School(
                "123",
                "Fake School"
            ),
            School(
                "1123",
                "Fake School1"
            )
        ))
        assertEquals("1123", viewModel.schools.value[1].dbn)
        repo.emitSchool(
            emptyList()
        )
        assertEquals(true, viewModel.uiState.first().isError)
    }

}