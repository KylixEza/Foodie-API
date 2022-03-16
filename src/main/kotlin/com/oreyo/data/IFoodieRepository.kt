package com.oreyo.data

import com.oreyo.model.challenge.ChallengeResponse
import com.oreyo.model.coupon.CouponResponse
import com.oreyo.model.favorite.FavoriteBody
import com.oreyo.model.favorite.FavoriteResponse
import com.oreyo.model.ingredient.IngredientResponse
import com.oreyo.model.menu.MenuResponse
import com.oreyo.model.note.NoteBody
import com.oreyo.model.note.NoteResponse
import com.oreyo.model.review.ReviewRequest
import com.oreyo.model.review.ReviewResponse
import com.oreyo.model.step.StepResponse
import com.oreyo.model.transaction.TransactionBody
import com.oreyo.model.transaction.TransactionResponse
import com.oreyo.model.user.UserBody
import com.oreyo.model.user.UserResponse
import com.oreyo.model.variant.VariantResponse
import com.oreyo.model.voucher.VoucherResponse
import com.oreyo.model.voucher_user.VoucherUserResponse

interface IFoodieRepository {
	suspend fun addUser(body: UserBody)
	suspend fun getDetailUser(uid: String): UserResponse
	suspend fun updateUser(uid: String, body: UserBody)
	suspend fun getLeaderboard(): List<Int>
	suspend fun addFavorite(body: FavoriteBody)
	suspend fun getAllFavoritesByUser(uid: String): List<FavoriteResponse>
	suspend fun getAllCoupons(): List<CouponResponse>
	suspend fun getAllMenus(): List<MenuResponse>
	suspend fun getCategoryMenus(category: String): List<MenuResponse?>
	suspend fun getDietMenus(): List<MenuResponse>
	suspend fun getPopularMenus(): List<MenuResponse>
	suspend fun getExclusiveMenus(): List<MenuResponse>
	suspend fun getDetailMenu(menuId: String): MenuResponse
	suspend fun updateMenuOrder(menuId: String)
	suspend fun searchMenu(query: String): List<MenuResponse>
	suspend fun getIngredients(menuId: String): List<IngredientResponse>
	suspend fun getSteps(menuId: String): List<StepResponse>
	suspend fun getReviews(menuId: String, request: ReviewRequest): List<ReviewResponse>
	suspend fun getAllVariants(menuId: String): List<VariantResponse>
	suspend fun addNewTransaction(body: TransactionBody)
	suspend fun getAllTransaction(uid: String): List<TransactionResponse>
	suspend fun getAvailableVoucher(uid: String): List<VoucherResponse>
	suspend fun getVoucherUser(uid: String): List<VoucherUserResponse>
	suspend fun getDetailVoucher(voucherId: String): VoucherResponse
	suspend fun getCaloriesPrediction(body: NoteBody): Int?
	suspend fun addNewNote(body: NoteBody)
	suspend fun getAllNoteByUser(uid: String): List<NoteResponse>
	suspend fun getAllAvailableChallenge(): List<ChallengeResponse>
}