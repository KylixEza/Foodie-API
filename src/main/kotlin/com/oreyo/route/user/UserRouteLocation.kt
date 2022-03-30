package com.oreyo.route.user

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
class UserRouteLocation {
	companion object {
		private const val USER = "/user"
		//POST
		const val POST_USER = USER
		const val SELECTED_USER = "$USER/{uid}"
		//GET
		const val DETAIL_USER = SELECTED_USER
		//UPDATE
		const val UPDATE_USER = SELECTED_USER
		//POST
		const val POST_FAVORITE = "$SELECTED_USER/favorite"
		//GET
		const val FAVORITES = "$SELECTED_USER/favorite"
		//DELETE
		const val DELETE_FAVORITE = "$SELECTED_USER/favorite"
		//GET
		const val LEADERBOARD = "/leaderboard"
		//GET
		const val TRANSACTIONS = "$SELECTED_USER/transaction"
		//POST
		const val POST_HISTORY = "$SELECTED_USER/history"
		//UPDATE
		const val UPDATE_HISTORY_STATUS = "$SELECTED_USER/history/status"
		//UPDATE
		const val UPDATE_HISTORY_STARS = "$SELECTED_USER/history/stars"
		//GET
		const val GET_HISTORY = "$SELECTED_USER/history"
	}
	
	@Location(POST_USER)
	class UserAddRoute
	
	@Location(DETAIL_USER)
	data class UserGetDetailRoute(val uid: String)
	
	@Location(UPDATE_USER)
	data class UserUpdateRoute(val uid: String)
	
	@Location(POST_FAVORITE)
	data class FavoriteAddRoute(val uid: String)
	
	@Location(DELETE_FAVORITE)
	data class FavoriteDeleteRoute(val uid: String)
	
	@Location(FAVORITES)
	data class FavoriteGetListRoute(val uid: String)
	
	@Location(LEADERBOARD)
	class LeaderBoardGetListRoute
	
	@Location(TRANSACTIONS)
	data class TransactionGetListRoute(val uid: String)
	
	@Location(POST_HISTORY)
	data class HistoryPostRoute(val uid: String)
	
	@Location(UPDATE_HISTORY_STATUS)
	data class HistoryUpdateStatusRoute(val uid: String)
	
	@Location(UPDATE_HISTORY_STARS)
	data class HistoryUpdateStarsRoute(val uid: String)
	
	@Location(GET_HISTORY)
	data class HistoryGetListRoute(val uid: String)
}