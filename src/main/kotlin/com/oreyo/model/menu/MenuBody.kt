package com.oreyo.model.menu

import com.google.gson.annotations.SerializedName

data class MenuBody(
	@field:SerializedName("ordered")
	val ordered: Int
)
