package com.oreyo.data

import com.oreyo.model.challenge.ChallengeBody
import com.oreyo.model.challenge.ChallengeResponse
import com.oreyo.model.coupon.CouponBody
import com.oreyo.model.coupon.CouponResponse
import com.oreyo.model.favorite.FavoriteBody
import com.oreyo.model.favorite.FavoriteResponse
import com.oreyo.model.history.HistoryBody
import com.oreyo.model.history.HistoryUpdateBody
import com.oreyo.model.history.HistoryUpdateStarsGiven
import com.oreyo.model.ingredient.IngredientBody
import com.oreyo.model.ingredient.IngredientResponse
import com.oreyo.model.menu.MenuBody
import com.oreyo.model.menu.MenuResponse
import com.oreyo.model.note.NoteBody
import com.oreyo.model.note.NoteResponse
import com.oreyo.model.review.ReviewResponse
import com.oreyo.model.step.StepBody
import com.oreyo.model.step.StepResponse
import com.oreyo.model.history.TransactionResponse
import com.oreyo.model.leaderboard.LeaderBoardResponse
import com.oreyo.model.review.ReviewBody
import com.oreyo.model.user.UserBody
import com.oreyo.model.user.UserResponse
import com.oreyo.model.variant.VariantBody
import com.oreyo.model.variant.VariantResponse
import com.oreyo.model.voucher.VoucherBody
import com.oreyo.model.voucher.VoucherResponse
import com.oreyo.model.voucher_user.VoucherUserResponse

interface IFoodieRepository {
	suspend fun addUser(body: UserBody) //clear
	suspend fun getDetailUser(uid: String): UserResponse //clear
	suspend fun updateUser(uid: String, body: UserBody) //clear
	suspend fun getLeaderboard(): List<LeaderBoardResponse>  //clear
	suspend fun addFavorite(uid: String, body: FavoriteBody) //clear
	suspend fun getAllFavoritesByUser(uid: String): List<FavoriteResponse> //clear
	suspend fun addNewCoupon(body: CouponBody) //clear
	suspend fun getAllCoupons(): List<CouponResponse> //clear
	suspend fun addNewMenu(body: MenuBody) //clear
	suspend fun getAllMenus(): List<MenuResponse> //clear (note: add default value at data class in front-end side)
	suspend fun getCategoryMenus(category: String): List<MenuResponse?> //clear
	suspend fun getDietMenus(): List<MenuResponse> //clear
	suspend fun getPopularMenus(): List<MenuResponse> //clear
	suspend fun getExclusiveMenus(): List<MenuResponse> //clear
	suspend fun getDetailMenu(menuId: String): MenuResponse //clear
	suspend fun updateMenuOrder(menuId: String) //clear
	suspend fun searchMenu(query: String): List<MenuResponse> //clear
	suspend fun addNewIngredient(menuId: String, body: IngredientBody) //clear
	suspend fun getIngredients(menuId: String): List<IngredientResponse> //clear
	suspend fun addNewStep(menuId: String, body: StepBody) //clear
	suspend fun getSteps(menuId: String): List<StepResponse> //clear
	suspend fun addNewReview(menuId: String, body: ReviewBody) //clear
	suspend fun getReviews(menuId: String): List<ReviewResponse> //clear
	suspend fun addNewVariant(menuId: String, body: VariantBody) //clear
	suspend fun getAllVariants(menuId: String): List<VariantResponse> //clear
	suspend fun getAllLastTransaction(uid: String): List<TransactionResponse>
	suspend fun addNewHistory(uid: String, body: HistoryBody)
	suspend fun updateHistoryStatus(body: HistoryUpdateBody)
	suspend fun updateHistoryStarsGiven(uid: String, body: HistoryUpdateStarsGiven)
	suspend fun getAllHistoryByUser(uid: String)
	suspend fun addNewVoucher(body: VoucherBody)
	suspend fun getAvailableVoucher(uid: String): List<VoucherResponse>
	suspend fun getVoucherUser(uid: String): List<VoucherUserResponse>
	suspend fun getDetailVoucher(voucherId: String): VoucherResponse
	suspend fun getCaloriesPrediction(body: NoteBody): Int?
	suspend fun addNewNote(body: NoteBody)
	suspend fun getAllNoteByUser(uid: String): List<NoteResponse>
	suspend fun addNewChallenge(body: ChallengeBody)
	suspend fun getAllAvailableChallenge(): List<ChallengeResponse>
}