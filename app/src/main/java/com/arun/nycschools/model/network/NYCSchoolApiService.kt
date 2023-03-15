package com.arun.nycschools.model.network

import com.arun.nycschools.model.data.School
import com.arun.nycschools.model.data.SchoolMarks
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton
/**
 * [NYCSchoolApiService] class is responsible for utilizing retrofit apis to obtain data from
 * the backend
 * */
@Singleton
class NYCSchoolApiService {

    private val BASE_URL = "https://data.cityofnewyork.us/resource/"

    private var api: NYCSchoolApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(NYCSchoolApi::class.java)

    /**
     * [getSchoolList] method that calls [NYCSchoolApi] retrofit interface function and
     *
     * @return listOf ( [School] ) list of School objects
     * */
    suspend fun getSchoolList(): List<School> {
        return api.getSchools()
    }

    /**
     * [getSchoolMarks] method that calls [NYCSchoolApi] retrofit interface function
     *
     * @return listOf ( [SchoolMarks] ) list of SchoolMarks objects
     * */
    suspend fun getSchoolMarks(dbn: String): List<SchoolMarks> {
        return api.getSchoolMarks(dbn)
    }

    interface NYCSchoolApi {

        @GET("s3k6-pzi2.json")
        suspend fun getSchools(): List<School>

        @GET("f9bf-2cp4.json")
        suspend fun getSchoolMarks(@Query("dbn") dbn: String) : List<SchoolMarks>
    }
}