package com.oreyo.route.user

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
class UserRouteLocation {
	companion object {
		private const val USER = "/user"
		//POST
		const val ADD_USER = USER
		const val SELECTED_USER = "$USER/{uid}"
		//GET
		const val DETAIL_USER = SELECTED_USER
		//UPDATE
		const val UPDATE_USER = SELECTED_USER
		//POST
		const val ADD_FAVORITE = "$SELECTED_USER/favorite"
		//GET
		const val FAVORITES = "$SELECTED_USER/favorite"
		//GET
		const val LEADERBOARD = "/leaderboard"
		//POST
		const val ADD_TRANSACTION = "$SELECTED_USER/transaction"
		//GET
		const val TRANSACTIONS = "$SELECTED_USER/transaction"
		//GET
		const val AVAILABLE_VOUCHER = "$SELECTED_USER/voucher/available"
		//GET
		const val OWN_VOUCHER = "$SELECTED_USER/voucher/own"
		//GET
		const val DETAIL_VOUCHER = "/voucher/{voucherId}"
	}
	
	@Location(ADD_USER)
	class UserAddRoute
	
	@Location(DETAIL_USER)
	data class UserGetDetailRoute(val uid: String)
	
	@Location(UPDATE_USER)
	data class UserUpdateRoute(val uid: String)
	
	@Location(ADD_FAVORITE)
	data class FavoriteAddRoute(val uid: String)
	
	@Location(FAVORITES)
	data class FavoriteGetListRoute(val uid: String)
	
	@Location(LEADERBOARD)
	class LeaderBoardGetListRoute
	
	@Location(ADD_TRANSACTION)
	data class TransactionPostRoute(val uid: String)
	
	@Location(TRANSACTIONS)
	data class TransactionGetListRoute(val uid: String)
	
	@Location(AVAILABLE_VOUCHER)
	data class VoucherAvailableGetListRoute(val uid: String)
	
	@Location(OWN_VOUCHER)
	data class VoucherOwnGetListRoute(val uid: String)
	
	@Location(DETAIL_VOUCHER)
	data class VoucherGetDetailRoute(val voucherId: String)
}