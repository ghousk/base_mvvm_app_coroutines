//*********************************************************************
// Created by Ghous Khan on 2020-10-02.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.base_mvvm_app_coroutines.db

import androidx.room.TypeConverter
import timber.log.Timber

object AppTypeConverters {
    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toInt()
                } catch (ex: NumberFormatException) {
                    Timber.e(ex, "Cannot convert $it to number")
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }
}
