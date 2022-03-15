package com.oreyo.model.challenge

import com.google.gson.annotations.SerializedName

data class ChallengeResponse(
	@field:SerializedName("challenge_id")
	val challengeId: String,
	
	@field:SerializedName("title")
	val title: String,
	
	@field:SerializedName("description")
	val description: String,
	
	@field:SerializedName("participant")
	val participant: Int,
	
	@field:SerializedName("xp_earned")
	val xpEarned: Int
)
