package com.innovativequest.base_mvvm_app_coroutines.model

import com.google.gson.annotations.SerializedName

data class BadgeCounts(

	@field:SerializedName("gold")
	val gold: Int? = null,

	@field:SerializedName("silver")
	val silver: Int? = null,

	@field:SerializedName("bronze")
	val bronze: Int? = null
)