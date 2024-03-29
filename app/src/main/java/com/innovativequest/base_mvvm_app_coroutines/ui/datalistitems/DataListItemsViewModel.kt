//*********************************************************************
// Created by Ghous Khan on 2020-10-02.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.base_mvvm_app_coroutines.ui.datalistitems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.innovativequest.base_mvvm_app_coroutines.repository.DataListItemsRepository
import com.innovativequest.base_mvvm_app_coroutines.testing.OpenForTesting
import com.innovativequest.base_mvvm_app_coroutines.util.AbsentLiveData
import com.innovativequest.base_mvvm_app_coroutines.model.DataListItemResponse
import com.innovativequest.base_mvvm_app_coroutines.model.Resource
import javax.inject.Inject

@OpenForTesting
class DataListItemsViewModel
@Inject constructor(private val repository: DataListItemsRepository) : ViewModel() {
    private val _dataListItemId: MutableLiveData<DataListItemId> = MutableLiveData()
    val dataListItemId: LiveData<DataListItemId>
        get() = _dataListItemId
    private val _dataListItems: MutableLiveData<Resource<DataListItemResponse>> = MutableLiveData()
    val dataListItems: LiveData<Resource<DataListItemResponse>>
        get() = _dataListItems

    fun loadDataListItems(){
        repository.loadDataListItemResponses().observeForever { _dataListItems.postValue(it) }
    }

    fun retry() {
        val id = _dataListItemId.value?.id
        if (id != null) {
            _dataListItemId.value = DataListItemId(id)
        }
    }

    data class DataListItemId(val id: String) {
        fun <T> ifExists(f: (String) -> LiveData<T>): LiveData<T> {
            return if (id.isBlank()) {
                AbsentLiveData.create()
            } else {
                f(id)
            }
        }
    }
}
