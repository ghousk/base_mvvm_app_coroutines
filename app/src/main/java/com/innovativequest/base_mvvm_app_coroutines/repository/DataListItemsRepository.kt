//*********************************************************************
// Created by Ghous Khan on 2020-10-02.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.base_mvvm_app_coroutines.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innovativequest.base_mvvm_app_coroutines.AppExecutors
import com.innovativequest.base_mvvm_app_coroutines.api.ApiResponse
import com.innovativequest.base_mvvm_app_coroutines.api.DataService
import com.innovativequest.base_mvvm_app_coroutines.db.DataListItemResponseDao
import com.innovativequest.base_mvvm_app_coroutines.testing.OpenForTesting
import com.innovativequest.base_mvvm_app_coroutines.util.RateLimiter
import com.innovativequest.base_mvvm_app_coroutines.model.*
import com.innovativequest.base_mvvm_app_coroutines.util.AppConstants
import com.innovativequest.base_mvvm_app_coroutines.util.AppConstants.REFRESH_TIME_OUT_IN_MILLIS
import com.innovativequest.base_mvvm_app_coroutines.util.PreferencesManager
import com.innovativequest.base_mvvm_app_coroutines.util.Utils
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Repo instances.
 *
 * unfortunate naming :/ .
 * Repo - value object name
 * Repository - type of this class.
 */
@Singleton
@OpenForTesting
class DataListItemsRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val preferencesManager: PreferencesManager,
    private val dataListItemDao: DataListItemResponseDao,
    private val dataService: DataService
) {

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)
    private val dataListItemResponseData = MutableLiveData<DataListItemResponse>()


    fun loadDataListItemResponses(): LiveData<Resource<DataListItemResponse>> {
        return object : NetworkBoundResource<DataListItemResponse, DataListItemResponse>(appExecutors) {
            override fun saveCallResult(item: DataListItemResponse) {
                dataListItemResponseData.postValue(item)
                dataListItemDao.insertDataListItemResponses(item)
            }

            override fun shouldFetch(data: DataListItemResponse?): Boolean {
                return data?.items == null ||data.items.isEmpty() ||
                        isOutdated(preferencesManager.getLong(AppConstants.GET_LIST_ITEMS))/*|| repoListRateLimit.shouldFetch()*/  // TODO: Enable this
            }

            override fun loadFromDb() = dataListItemDao.loadAllDataListItemResponses()

            override fun createCall()  : LiveData<ApiResponse<DataListItemResponse>> {
                preferencesManager.setLong(AppConstants.GET_LIST_ITEMS, Utils.getCurrentTime())
                return dataService.getDataItems(100, "desc", "reputation", "stackoverflow")
            }

            override fun onFetchFailed() {
                //repoListRateLimit.reset() // TODO: Enable this
            }
        }.asLiveData()
    }

    fun loadDataListItemResponseById(userId: String): LiveData<Resource<DataListItemResponse>> {
        return object : NetworkBoundResource<DataListItemResponse, DataListItemResponse>(appExecutors) {
            override fun saveCallResult(item: DataListItemResponse) {
                dataListItemDao.insertDataListItemResponses(item)
            }

            override fun shouldFetch(data: DataListItemResponse?): Boolean {
                return data?.items == null ||data.items.isEmpty() ||
                        isOutdated(preferencesManager.getLong(AppConstants.GET_LIST_ITEM_BY_ID + userId))/*|| repoListRateLimit.shouldFetch()*/  // TODO: Enable this
            }

            override fun loadFromDb() = dataListItemDao.loadAllDataListItemResponses()

            override fun createCall() : LiveData<ApiResponse<DataListItemResponse>> {
                preferencesManager.setLong(AppConstants.GET_LIST_ITEM_BY_ID + userId, Utils.getCurrentTime())

                return dataService.getDataItemById(userId,100, "desc", "reputation", "stackoverflow")
            }

            override fun onFetchFailed() {
                //repoListRateLimit.reset() // TODO: Enable this
            }
        }.asLiveData()
    }

    fun dataListItemResponse(): LiveData<DataListItemResponse> = dataListItemResponseData

    /**
     * Check if a time stamp from prefs is outdated
     */
    private fun isOutdated(lastUpdateTime: Long): Boolean = Utils.getCurrentTime() > lastUpdateTime + REFRESH_TIME_OUT_IN_MILLIS
}
