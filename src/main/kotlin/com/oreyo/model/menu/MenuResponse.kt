package com.oreyo.model.menu

import com.google.gson.annotations.SerializedName

data class MenuResponse(
	
	@field:SerializedName("menu_id")
	val menuId: String,
	
	@field:SerializedName("difficulty")
	val difficulty: String,
	
	@field:SerializedName("calories")
	val calories: String,
	
	@field:SerializedName("image")
	val image: String,
	
	@field:SerializedName("price")
	val price: Int,
	
	@field:SerializedName("rating")
	val rating: Double,
	
	@field:SerializedName("estimated_time")
	val estimatedTime: Int,
	
	@field:SerializedName("title")
	val title: String,
	
	@field:SerializedName("type")
	val type: String,
)
