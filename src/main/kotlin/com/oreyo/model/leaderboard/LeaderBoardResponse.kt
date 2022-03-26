package com.oreyo.model.leaderboard

import com.google.gson.annotations.SerializedName

data class LeaderBoardResponse(
	
	@field:SerializedName("uid")
	val uid: String,
	
	@field:SerializedName("name")
	val name: String,
	
	@field:SerializedName("xp")
	val xp: Int
)
