//*********************************************************************
// Created by Ghous Khan on 2020-10-02.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.base_mvvm_app_coroutines.api

import androidx.lifecycle.LiveData
import com.innovativequest.base_mvvm_app_coroutines.util.AppConstants
import com.innovativequest.base_mvvm_app_coroutines.model.DataListItemResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * REST API access points
 */
interface DataService {

    @GET(AppConstants.GET_LIST_ITEMS)
    fun getDataItems(
        @Query("pagesize") count: Int,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("site") site: String
    ): LiveData<ApiResponse<DataListItemResponse>>

    @GET(AppConstants.GET_LIST_ITEM_BY_ID)
    fun getDataItemById(
        @Path("userId") userId : String,
        @Query("pagesize") count: Int,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("site") site: String
    ): LiveData<ApiResponse<DataListItemResponse>>
}
