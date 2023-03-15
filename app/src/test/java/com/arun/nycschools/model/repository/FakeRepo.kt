package com.arun.nycschools.model.repository

import com.arun.nycschools.model.data.School
import com.arun.nycschools.model.data.SchoolMarks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FakeRepo: NYCSchoolRepo {
    private val schoolFlow = MutableSharedFlow<List<School>>()
    suspend fun emitSchool(value: List<School>) = schoolFlow.emit(value)
    override suspend fun getSchools() = schoolFlow

    private val marksFlow = MutableSharedFlow<List<SchoolMarks>>()
    suspend fun emitSchoolMarks(value: List<SchoolMarks>) = marksFlow.emit(value)
    override suspend fun getSatMarks(dbn: String) = marksFlow
}