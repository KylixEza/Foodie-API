package com.oreyo.route.menu

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
}
