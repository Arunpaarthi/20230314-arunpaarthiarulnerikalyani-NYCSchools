package com.arun.nycschools.di


import com.arun.nycschools.model.network.NYCSchoolApiService
import com.arun.nycschools.model.repository.NYCSchoolRepo
import com.arun.nycschools.model.repository.NYCSchoolRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NYCHiltModule {

    @Binds
    @Singleton
    abstract fun getApiService(impl: NYCSchoolRepository) : NYCSchoolRepo
}

@Module
@InstallIn(SingletonComponent::class)
object HiltProvider {

    @Provides
    @Singleton
    fun provideApiService() : NYCSchoolApiService {
        return NYCSchoolApiService()
    }
}