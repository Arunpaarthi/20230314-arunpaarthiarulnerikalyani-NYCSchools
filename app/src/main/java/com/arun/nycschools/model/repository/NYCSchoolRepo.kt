package com.arun.nycschools.model.repository

import com.arun.nycschools.model.data.School
import com.arun.nycschools.model.data.SchoolMarks
import kotlinx.coroutines.flow.Flow

/**
 * [NYCSchoolRepo] interface is responsible for binding Repository at runtime and expose
 * methods to obtain data
 * */
interface NYCSchoolRepo {

    suspend fun getSchools() : Flow<List<School>>

    suspend fun getSatMarks(dbn: String): Flow<List<SchoolMarks>>
}