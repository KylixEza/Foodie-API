package com.oreyo.route.user

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
		const val GET_FAVORITES = "$SELECTED_USER/favorite"
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
}