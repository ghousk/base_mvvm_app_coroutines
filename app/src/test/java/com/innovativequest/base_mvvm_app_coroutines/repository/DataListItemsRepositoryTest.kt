//*********************************************************************
// Created by Ghous Khan on 2024-02-14.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.base_mvvm_app_coroutines.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.innovativequest.base_mvvm_app_coroutines.api.DataService
import com.innovativequest.base_mvvm_app_coroutines.db.DataListItemResponseDao
import com.innovativequest.base_mvvm_app_coroutines.util.InstantAppExecutors
import com.innovativequest.base_mvvm_app_coroutines.util.PreferencesManager
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertFails
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class DataListItemsRepositoryTest {
    private lateinit var repository: DataListItemsRepository
    private lateinit var dao: DataListItemResponseDao
    private lateinit var service: DataService
    private lateinit var preferencesManager: PreferencesManager
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        preferencesManager = mock()
        dao = mock()
        service = mock()
        repository = DataListItemsRepository(InstantAppExecutors(), preferencesManager, dao, service)
    }

    @Test
    fun when_repository_loads_data_with_success(){
        repository.loadDataListItemResponses()
        repository.dataListItemResponse().observeForever { assertTrue { it.items?.size!! > 0  }}
    }

    @Test
    fun when_repository_loads_data_item_by_id_with_success(){
        repository.loadDataListItemResponseById("6309")
        repository.dataListItemResponse().observeForever { assertTrue { it.items?.get(0)?.userId!! == 6309  }}
    }

    @Test
    fun when_repository_loads_data_item_by_id_with_failure(){
        repository.loadDataListItemResponseById("1234567890")
        repository.dataListItemResponse().observeForever { assertFails { } }
    }
}