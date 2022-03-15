package com.oreyo.model.transaction

import com.google.gson.annotations.SerializedName

data class TransactionBody(
	
	@field:SerializedName("uid")
	val uid: String,
	
	@field:SerializedName("variant")
	val variant: String,
	
	@field:SerializedName("price")
	val price: Int
)
