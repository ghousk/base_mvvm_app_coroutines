//*********************************************************************
// Created by Ghous Khan on 2020-10-02.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.base_mvvm_app_coroutines.di

import com.innovativequest.base_mvvm_app_coroutines.ui.detail.ItemDetailFragment
import com.innovativequest.base_mvvm_app_coroutines.ui.datalistitems.DataListItemsFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): ItemDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeDataListItemsFragment(): DataListItemsFragment
}
