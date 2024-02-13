//*********************************************************************
// Created by Ghous Khan on 2020-10-02.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.base_mvvm_app_coroutines.ui.datalistitems

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.innovativequest.base_mvvm_app_coroutines.repository.DataListItemsRepository
import com.innovativequest.base_mvvm_app_coroutines.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

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

        SUT.dataListItems.observeForever(mock())
        verifyNoMoreInteractions(dataListItemsRepository)
        verify(dataListItemsRepository).loadDataListItemResponses()
        reset(dataListItemsRepository)
        verifyNoMoreInteractions(dataListItemsRepository)
    }

}