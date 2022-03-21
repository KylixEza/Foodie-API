package com.oreyo.route.challenge

import com.oreyo.data.IFoodieRepository
import com.oreyo.model.challenge.ChallengeBody
import com.oreyo.route.RouteResponseHelper.generalException
import com.oreyo.route.RouteResponseHelper.generalListSuccess
import com.oreyo.route.RouteResponseHelper.generalSuccess
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.request.*

@KtorExperimentalLocationsAPI
class ChallengeRoute(
	private val repository: IFoodieRepository
) {
	
	private fun Route.getAllAvailableChallenge() {
		get<ChallengeRouteLocation.ChallengeGetListRoute> {
			call.generalListSuccess { repository.getAllAvailableChallenge() }
		}
	}
	
	private fun Route.postChallenge() {
		post<ChallengeRouteLocation.ChallengePostRoute> {
			val body = try {
				call.receive<ChallengeBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			call.generalSuccess { repository.addNewChallenge(body) }
		}
	}
	
	fun initChallengeRoute(route: Route) {
		route.apply {
			getAllAvailableChallenge()
			postChallenge()
		}
	}
	
}