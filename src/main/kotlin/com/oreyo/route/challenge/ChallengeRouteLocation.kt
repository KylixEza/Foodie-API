package com.oreyo.route.challenge

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
sealed class ChallengeRouteLocation {
	companion object {
		//GET
		const val CHALLENGE = "/challenge"
	}
	
	@Location(CHALLENGE)
	class ChallengeGetListRoute
}
