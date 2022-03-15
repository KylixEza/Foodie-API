package com.oreyo.route.menu

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
sealed class MenuRouteLocation {
	companion object {
		//GET (Include query to search menu)
		const val MENUS = "/menus"
		//GET
		const val COUPONS = "/coupons"
		//Using query to get category
		//GET
		const val CATEGORY = "$MENUS/category"
		//GET
		const val DIET = "$MENUS/diet"
		//GET
		const val POPULAR = "$MENUS/popular"
		//GET
		const val EXCLUSIVE = "$MENUS/exclusive"
		//GET
		const val DETAIL_MENU = "$MENUS/{menuId}"
		//UPDATE
		const val UPDATE_ORDER = "$MENUS/{menuId}/order"
		//GET
		const val INGREDIENTS = "$MENUS/{menuId}/ingredient"
		//GET
		const val STEPS = "$MENUS/{menuId}/step"
		//GET
		const val REVIEWS = "$MENUS/{menuId}/review"
		//GET
		const val VARIANTS = "$MENUS/{menuId}/variant"
	}
	
	@Location(MENUS)
	class MenuGetListRoute
	
	@Location(COUPONS)
	class CouponGetListRoute
	
	@Location(CATEGORY)
	class CategoryGetListRoute
	
	@Location(DIET)
	class DietGetListRoute
	
	@Location(POPULAR)
	class PopularGetListRoute
	
	@Location(EXCLUSIVE)
	class ExclusiveGetListRoute
	
	@Location(DETAIL_MENU)
	data class MenuGetDetailRoute(val menuId: String)
	
	@Location(UPDATE_ORDER)
	data class MenuUpdateOrderRoute(val menuId: String)
	
	@Location(INGREDIENTS)
	data class MenuGetIngredientListRoute(val menuId: String)
	
	@Location(STEPS)
	data class MenuGetStepListRoute(val menuId: String)
	
	@Location(REVIEWS)
	data class MenuGetReviewListRoute(val menuId: String)
	
	@Location(VARIANTS)
	data class MenuGetVariantListRoute(val menuId: String)
}
