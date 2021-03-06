package com.oreyo.route.user

import com.oreyo.data.IFoodieRepository
import com.oreyo.model.favorite.FavoriteBody
import com.oreyo.model.history.HistoryBody
import com.oreyo.model.history.HistoryUpdateBody
import com.oreyo.model.history.HistoryUpdateStarsGiven
import com.oreyo.model.user.UserBody
import com.oreyo.route.RouteResponseHelper.generalException
import com.oreyo.route.RouteResponseHelper.generalListSuccess
import com.oreyo.route.RouteResponseHelper.generalSuccess
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.locations.put
import io.ktor.locations.post
import io.ktor.request.*

@KtorExperimentalLocationsAPI
class UserRoute(
	private val repository: IFoodieRepository
) {
	
	private fun Route.addNewUser() {
		post<UserRouteLocation.UserAddRoute> {
			val body = try {
				call.receive<UserBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			call.generalSuccess { repository.addUser(body) }
		}
	}
	
	private fun Route.getDetailUser() {
		get<UserRouteLocation.UserGetDetailRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalSuccess { repository.getDetailUser(uid!!) }
		}
	}
	
	private fun Route.updateUser() {
		put<UserRouteLocation.UserUpdateRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			}
			
			val body = try {
				call.receive<UserBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			}
			
			call.generalSuccess { repository.updateUser(uid!!, body) }
		}
	}
	
	private fun Route.addNewFavoriteByUser() {
		post<UserRouteLocation.FavoriteAddRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			val body = try {
				call.receive<FavoriteBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			call.generalSuccess { repository.addFavorite(uid!!, body) }
		}
	}
	
	private fun Route.deleteFavoriteByUser() {
		delete<UserRouteLocation.FavoriteDeleteRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@delete
			}
			
			val body = try {
				call.receive<FavoriteBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@delete
			}
			
			call.generalSuccess { repository.deleteFavorite(uid!!, body) }
		}
	}
	
	private fun Route.getFavoritesByUser() {
		get<UserRouteLocation.FavoriteGetListRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getAllFavoritesByUser(uid!!) }
		}
	}
	
	private fun Route.getLeaderboard() {
		get<UserRouteLocation.LeaderBoardGetListRoute> {
			call.generalListSuccess { repository.getLeaderboard() }
		}
	}
	
	private fun Route.getTransactionsByUser() {
		get<UserRouteLocation.TransactionGetListRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getAllLastTransaction(uid!!) }
		}
	}
	
	private fun Route.postHistory() {
		post<UserRouteLocation.HistoryPostRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			val body = try {
				call.receive<HistoryBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			call.generalSuccess { repository.addNewHistory(uid!!, body) }
		}
	}
	
	private fun Route.updateHistoryStatus() {
		put<UserRouteLocation.HistoryUpdateStatusRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			}
			
			val body = try {
				call.receive<HistoryUpdateBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			}
			
			call.generalSuccess { repository.updateHistoryStatus(body) }
		}
	}
	
	private fun Route.updateHistoryStarsGiven() {
		put<UserRouteLocation.HistoryUpdateStarsRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			}
			
			val body = try {
				call.receive<HistoryUpdateStarsGiven>()
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			}
			
			call.generalSuccess { repository.updateHistoryStarsGiven(uid!!, body) }
		}
	}
	
	private fun Route.getAllHistoryByUser() {
		get<UserRouteLocation.HistoryGetListRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			
			call.generalListSuccess { repository.getAllHistoryByUser(uid!!) }
		}
	}
	
	fun initUserRoute(route: Route) {
		route.apply {
			addNewUser()
			getDetailUser()
			updateUser()
			addNewFavoriteByUser()
			deleteFavoriteByUser()
			getFavoritesByUser()
			getLeaderboard()
			getTransactionsByUser()
			postHistory()
			updateHistoryStatus()
			updateHistoryStarsGiven()
			getAllHistoryByUser()
		}
	}
}
