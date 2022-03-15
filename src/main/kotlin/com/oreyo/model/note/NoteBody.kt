package com.oreyo.model.note

import com.google.gson.annotations.SerializedName
import com.oreyo.data.table.NoteTable
import com.oreyo.data.table.UserTable

data class NoteBody (
	
	@field:SerializedName("uid")
	val uid: String,
	
	@field:SerializedName("category")
	val category: String,
	
	@field:SerializedName("date")
	val date: String,
	
	@field:SerializedName("food")
	val food: String,
	
	@field:SerializedName("information")
	val information: String,
	
	@field:SerializedName("portion")
	val portion: Int
)