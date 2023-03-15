package com.arun.nycschools.model.repository

import com.arun.nycschools.model.data.School
import com.arun.nycschools.model.data.SchoolMarks
import kotlinx.coroutines.flow.Flow

interface NYCSchoolRepo {

    suspend fun getSchools() : Flow<List<School>>

    suspend fun getSatMarks(dbn: String): Flow<List<SchoolMarks>>
}