package com.oreyo.route.challenge

import com.oreyo.data.IFoodieRepository
import com.oreyo.route.RouteResponseHelper.generalException
import com.oreyo.route.RouteResponseHelper.generalListSuccess
import com.oreyo.route.RouteResponseHelper.generalSuccess
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@KtorExperimentalLocationsAPI
class ChallengeRoute(
	private val repository: IFoodieRepository
) {
	
	private fun Route.getAllAvailableChallenge() {
		get<ChallengeRouteLocation.ChallengeGetListRoute> {
			call.generalListSuccess { repository.getAllAvailableChallenge() }
		}
	}
	
	fun initChallengeRoute(route: Route) {
		route.apply {
			getAllAvailableChallenge()
		}
	}
	
}