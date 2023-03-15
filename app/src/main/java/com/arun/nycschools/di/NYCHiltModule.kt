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

/**
 * [NYCHiltModule] abstract class is a HILT module which will be used by HILT to Bind
 * the interfaces that this app owns
 * */
@Module
@InstallIn(SingletonComponent::class)
abstract class NYCHiltModule {

    @Binds
    @Singleton
    abstract fun getApiService(impl: NYCSchoolRepository) : NYCSchoolRepo
}

/**
 * [HiltProvider] object class is a HILT module which will provide the instances to the HILT library
 * */
@Module
@InstallIn(SingletonComponent::class)
object HiltProvider {

    @Provides
    @Singleton
    fun provideApiService() : NYCSchoolApiService {
        return NYCSchoolApiService()
    }
}