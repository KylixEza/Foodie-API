package com.oreyo.model.ingredient

import com.google.gson.annotations.SerializedName

data class IngredientBody(
	@field:SerializedName("menu_id")
	val menuId: String,
	
	@field:SerializedName("ingredient")
	val ingredient: String
)
