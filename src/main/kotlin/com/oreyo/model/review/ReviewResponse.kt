package com.oreyo.model.review

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
	@field:SerializedName("uid")
	val uid: String,
	
	@field:SerializedName("menu_id")
	val menuId: String,
	
	@field:SerializedName("rating")
	val rating: Double
)
