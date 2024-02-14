//*********************************************************************
// Created by Ghous Khan on 2020-02-14.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.base_mvvm_app_coroutines.ui.datalistitems

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.innovativequest.base_mvvm_app_coroutines.repository.DataListItemsRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class DataListItemsViewModelTest {

    private lateinit var SUT: DataListItemsViewModel
    private lateinit var dataListItemsRepository: DataListItemsRepository

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        dataListItemsRepository = mock(DataListItemsRepository::class.java)
        SUT = DataListItemsViewModel(dataListItemsRepository)
    }

    @Test
    fun loadRepositories() {
        SUT.dataListItems.observeForever{ assertTrue { it.data?.items?.size!! > 0 }}
    }

}