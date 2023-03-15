package com.arun.nycschools.model.repository

import android.util.Log
import com.arun.nycschools.model.data.School
import com.arun.nycschools.model.data.SchoolMarks
import com.arun.nycschools.model.network.NYCSchoolApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [NYCSchoolRepository] class is responsible for calling the API and obtaining the data for the App
 * */
@Singleton
class NYCSchoolRepository @Inject constructor(private val api: NYCSchoolApiService): NYCSchoolRepo {

    companion object {
        private const val TAG = "NYCSchoolRepository"
    }

    /**
     * [getSchools] method calls the getSchoolList from the ApiService and expose the data as flow
     * */
    override suspend fun getSchools(): Flow<List<School>> {
        return flow {
            emit(api.getSchoolList())
        }.catch {
            Log.d(TAG, "Error - ${it.message}")
            emit(emptyList())
        }.flowOn(Dispatchers.IO).conflate()
    }

    /**
     * [getSatMarks] method calls the getSchoolMarks from the ApiService and expose the data as flow
     *
     * @param dbn - DBN of the school for which we are requesting data
     * */
    override suspend fun getSatMarks(dbn: String): Flow<List<SchoolMarks>> {
        return flow {
            emit(api.getSchoolMarks(dbn))
        }.catch {
            Log.d(TAG, "Error - ${it.message}")
            emit(emptyList())
        }.flowOn(Dispatchers.IO).conflate()
    }
}