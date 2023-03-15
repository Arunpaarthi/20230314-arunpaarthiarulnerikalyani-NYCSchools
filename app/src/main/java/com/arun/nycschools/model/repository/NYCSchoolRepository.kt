package com.arun.nycschools.model.repository

import android.util.Log
import com.arun.nycschools.model.data.School
import com.arun.nycschools.model.data.SchoolMarks
import com.arun.nycschools.model.network.NYCSchoolApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NYCSchoolRepository @Inject constructor(private val api: NYCSchoolApiService): NYCSchoolRepo {

    companion object {
        private const val TAG = "NYCSchoolRepository"
    }

    override suspend fun getSchools(): Flow<List<School>> {
        return flow {
            emit(api.getSchoolList())
        }.catch {
            Log.d(TAG, "Error - ${it.message}")
            emit(emptyList())
        }.flowOn(Dispatchers.IO).conflate()
    }

    override suspend fun getSatMarks(dbn: String): Flow<List<SchoolMarks>> {
        return flow {
            emit(api.getSchoolMarks(dbn))
        }.catch {
            Log.d(TAG, "Error - ${it.message}")
            emit(emptyList())
        }.flowOn(Dispatchers.IO).conflate()
    }
}