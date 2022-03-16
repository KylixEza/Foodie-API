package com.oreyo.route.menu

import com.oreyo.data.IFoodieRepository
import com.oreyo.model.review.ReviewRequest
import com.oreyo.route.RouteResponseHelper.generalException
import com.oreyo.route.RouteResponseHelper.generalListSuccess
import com.oreyo.route.RouteResponseHelper.generalSuccess
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.locations.put
import io.ktor.request.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
class MenuRoute(
	private val repository: IFoodieRepository
) {
	private fun Route.getAllMenus() {
		get<MenuRouteLocation.MenuGetListRoute> {
			val query = try {
				call.request.queryParameters["query"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			
			if (query != null)
				call.generalListSuccess { repository.searchMenu(query) }
			else
				call.generalListSuccess { repository.getAllMenus() }
		}
	}
	
	private fun Route.getAllCoupons()  {
		get<MenuRouteLocation.CouponGetListRoute> {
			call.generalListSuccess { repository.getAllCoupons() }
		}
	}
	
	private fun Route.getCategoryMenus() {
		get<MenuRouteLocation.CategoryGetListRoute> {
			val category = try {
				call.request.queryParameters["category"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			
			if (category != null)
				call.generalListSuccess { repository.getCategoryMenus(category) }
		}
	}
	
	private fun Route.getDietMenus() {
		get<MenuRouteLocation.DietGetListRoute> {
			call.generalListSuccess { repository.getDietMenus() }
		}
	}
	
	private fun Route.getPopularMenus() {
		get<MenuRouteLocation.PopularGetListRoute> {
			call.generalListSuccess { repository.getPopularMenus() }
		}
	}
	
	private fun Route.getExclusiveMenus() {
		get<MenuRouteLocation.ExclusiveGetListRoute> {
			call.generalListSuccess { repository.getExclusiveMenus() }
		}
	}
	
	private fun Route.getDetailMenu() {
		get<MenuRouteLocation.MenuGetDetailRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalSuccess { repository.getDetailMenu(menuId!!) }
		}
	}
	
	private fun Route.updateOrderMenu() {
		put<MenuRouteLocation.MenuUpdateOrderRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@put
			}
			call.generalSuccess { repository.updateMenuOrder(menuId!!) }
		}
	}
	
	private fun Route.getIngredients() {
		get<MenuRouteLocation.MenuGetIngredientListRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getIngredients(menuId!!) }
		}
	}
	
	private fun Route.getSteps() {
		get<MenuRouteLocation.MenuGetStepListRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getSteps(menuId!!) }
		}
	}
	
	private fun Route.getReviews() {
		get<MenuRouteLocation.MenuGetReviewListRoute> {
			val request = try {
				call.receive<ReviewRequest>()
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getReviews(menuId!!, request) }
		}
	}
	
	private fun Route.getVariants() {
		get<MenuRouteLocation.MenuGetVariantListRoute> {
			val menuId = try {
				call.parameters["menuId"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getAllVariants(menuId!!) }
		}
	}
}