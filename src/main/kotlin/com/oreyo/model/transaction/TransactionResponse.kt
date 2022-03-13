package com.oreyo.model.transaction

import com.google.gson.annotations.SerializedName

data class TransactionResponse(
	
	@field:SerializedName("uid")
	val uid: String,
	
	@field:SerializedName("transaction_id")
	val transactionId: String,
	
	@field:SerializedName("variant")
	val variant: String,
	
	@field:SerializedName("date")
	val date: String,
	
	@field:SerializedName("price")
	val price: Int
)
