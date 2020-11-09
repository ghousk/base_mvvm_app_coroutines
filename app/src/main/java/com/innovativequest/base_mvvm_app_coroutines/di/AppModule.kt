//*********************************************************************
// Created by Ghous Khan on 2020-10-02.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.base_mvvm_app_coroutines.di

import android.app.Application
import androidx.room.Room
import com.innovativequest.base_mvvm_app_coroutines.api.DataService
import com.innovativequest.base_mvvm_app_coroutines.db.AppDb
import com.innovativequest.base_mvvm_app_coroutines.db.DataListItemResponseDao
import com.innovativequest.base_mvvm_app_coroutines.util.AppConstants
import com.innovativequest.base_mvvm_app_coroutines.util.LiveDataCallAdapterFactory
import com.innovativequest.base_mvvm_app_coroutines.util.PreferencesManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideDataService(): DataService {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL_DEV)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(DataService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDb {
        return Room
            .databaseBuilder(app, AppDb::class.java, "app_data.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: AppDb): DataListItemResponseDao {
        return db.dataListItemResponseDao()
    }

    @Singleton
    @Provides
    fun providePreferencesManager(app: Application) = PreferencesManager(app.applicationContext)
}
