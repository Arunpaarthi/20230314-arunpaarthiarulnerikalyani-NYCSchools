package com.arun.nycschools.model.repository

import com.arun.nycschools.MainDispatcherRule
import com.arun.nycschools.model.data.School
import com.arun.nycschools.model.data.SchoolMarks
import com.arun.nycschools.model.network.NYCSchoolApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class NYCSchoolRepositoryTest {

    private lateinit var repo: NYCSchoolRepo
    private lateinit var excepRepo: NYCSchoolRepo

    var apiService =  NYCSchoolApiService()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var apiExcep: NYCSchoolApiService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repo = NYCSchoolRepository(apiService)
        excepRepo = NYCSchoolRepository(apiExcep)
    }

    @Test
    fun getSchools_Online() = runTest {
        assertEquals(true, repo.getSchools().first().isNotEmpty())
    }

    @Test
    fun getSchoolMarks() = runTest {
        // For now the backend API is returning empty list for this DBN in furture if this test case fails we need to modify
        assertEquals(true, repo.getSatMarks("02M260").first().isEmpty())
    }

    @Test
    fun getSchoolMarks_Empty() = runTest {
        assertEquals(true, repo.getSatMarks("21K728").first().isNotEmpty())
    }

    @Test
    fun getSchoolMarks_Invalid() = runTest {
        // For now the backend API is returning empty list for this DBN in furture if this test case fails we need to modify
        assertEquals(true, repo.getSatMarks("garbage").first().isEmpty())
    }

    @Test
    fun getSchoolMarks_Exception() = runTest {
        Mockito.`when`(apiExcep.getSchoolMarks("123")).thenAnswer {
            throw Exception("Test Exception")
        }
        assertEquals(true, excepRepo.getSatMarks("123").first().isEmpty())
    }

    @Test
    fun getSchools_Exception() = runTest {
        Mockito.`when`(apiExcep.getSchoolList()).thenAnswer {
            throw Exception("Test Exception")
        }
        assertEquals(true, excepRepo.getSchools().first().isEmpty())
    }

}