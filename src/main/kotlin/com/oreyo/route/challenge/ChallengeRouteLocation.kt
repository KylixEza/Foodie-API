package com.oreyo.route.challenge

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
sealed class ChallengeRouteLocation {
	companion object {
		//GET
		const val CHALLENGE = "/challenge"
		//POST
		const val POST_CHALLENGE = "/challenge"
	}
	
	@Location(CHALLENGE)
	class ChallengeGetListRoute
	
	@Location(POST_CHALLENGE)
	class ChallengePostRoute
}
