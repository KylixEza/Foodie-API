package com.oreyo.route.user

import com.oreyo.data.IFoodieRepository
import com.oreyo.model.favorite.FavoriteBody
import com.oreyo.model.transaction.TransactionBody
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
	
	private fun Route.postNewTransaction() {
		post<UserRouteLocation.TransactionPostRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			val body = try {
				call.receive<TransactionBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			
			call.generalSuccess { repository.addNewTransaction(uid!!, body) }
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
			call.generalListSuccess { repository.getAllTransaction(uid!!) }
		}
	}
	
	private fun Route.getAvailableVoucher() {
		get<UserRouteLocation.VoucherAvailableGetListRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getAvailableVoucher(uid!!) }
		}
	}
	
	private fun Route.getOwnVoucherByUser() {
		get<UserRouteLocation.VoucherOwnGetListRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getVoucherUser(uid!!) }
		}
	}
	
	private fun Route.getDetailVoucher() {
		get<UserRouteLocation.VoucherGetDetailRoute> {
			val voucherId = try {
				call.parameters["voucherId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalSuccess { repository.getDetailVoucher(voucherId!!) }
		}
	}
	
	fun initUserRoute(route: Route) {
		route.apply {
			addNewUser()
			getDetailUser()
			updateUser()
			addNewFavoriteByUser()
			getFavoritesByUser()
			getLeaderboard()
			postNewTransaction()
			getTransactionsByUser()
			getAvailableVoucher()
			getOwnVoucherByUser()
			getDetailVoucher()
		}
	}
}
