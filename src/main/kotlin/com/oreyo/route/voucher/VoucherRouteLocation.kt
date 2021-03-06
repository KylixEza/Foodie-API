package com.oreyo.route.voucher

import com.oreyo.route.user.UserRouteLocation.Companion.SELECTED_USER
import io.ktor.locations.*


@KtorExperimentalLocationsAPI
sealed class VoucherRouteLocation {
	companion object {
		//GET
		const val AVAILABLE_VOUCHER = "${SELECTED_USER}/voucher/available"
		//GET
		const val OWN_VOUCHER = "${SELECTED_USER}/voucher/own"
		//POST
		const val CLAIM_VOUCHER = "/voucher/{voucherId}/claim"
		//GET
		const val DETAIL_VOUCHER = "/voucher/{voucherId}"
		//POST
		const val POST_VOUCHER = "/voucher"
	}

	@Location(AVAILABLE_VOUCHER)
	data class VoucherAvailableGetListRoute(val uid: String)
	
	@Location(OWN_VOUCHER)
	data class VoucherOwnGetListRoute(val uid: String)
	
	@Location(CLAIM_VOUCHER)
	data class VoucherClaimRoute(val voucherId: String)
	
	@Location(DETAIL_VOUCHER)
	data class VoucherGetDetailRoute(val voucherId: String)
	
	@Location(POST_VOUCHER)
	class VoucherPostRoute
}
