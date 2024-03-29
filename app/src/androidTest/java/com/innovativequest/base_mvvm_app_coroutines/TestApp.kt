//*********************************************************************
// Created by Ghous Khan on 2020-10-02.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.base_mvvm_app_coroutines

import android.app.Application

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 * See [com.innovativequest.base_mvvm_app_coroutines.util.AppTestRunner].
 */
class TestApp : Application()
