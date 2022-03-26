package com.oreyo.data

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.oreyo.data.database.DatabaseFactory
import com.oreyo.data.table.*
import com.oreyo.model.challenge.ChallengeBody
import com.oreyo.model.coupon.CouponBody
import com.oreyo.model.favorite.FavoriteBody
import com.oreyo.model.history.HistoryBody
import com.oreyo.model.history.HistoryUpdateBody
import com.oreyo.model.history.HistoryUpdateStarsGiven
import com.oreyo.model.ingredient.IngredientBody
import com.oreyo.model.menu.MenuBody
import com.oreyo.model.note.NoteBody
import com.oreyo.model.review.ReviewRequest
import com.oreyo.model.step.StepBody
import com.oreyo.model.user.UserBody
import com.oreyo.model.variant.VariantBody
import com.oreyo.model.voucher.VoucherBody
import com.oreyo.util.Mapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.nield.kotlinstatistics.toNaiveBayesClassifier
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FoodieRepository(
	private val dbFactory: DatabaseFactory
): IFoodieRepository {
	
	override suspend fun addUser(body: UserBody) {
		dbFactory.dbQuery {
			UserTable.insert { table ->
				table[uid] = body.uid
				table[address] = body.address
				table[avatar] = body.avatar
				table[foodieWallet] = 0
				table[email] = body.email
				table[name] = body.name
				table[phoneNumber] = body.phoneNumber
				table[xp] = 0
			}
		}
	}
	
	override suspend fun getDetailUser(uid: String) = dbFactory.dbQuery {
		UserTable.select {
			UserTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToUserResponse(it)
		}
	}.first()
	
	override suspend fun updateUser(uid: String, body: UserBody) {
		dbFactory.dbQuery {
			UserTable.update(
				where = {UserTable.uid.eq(uid)}
			) { table ->
				table[address] = body.address
				table[avatar] = body.avatar
				table[foodieWallet] = body.foodieWallet
				table[email] = body.email
				table[name] = body.name
				table[phoneNumber] = body.phoneNumber
				table[xp] = body.xp
			}
		}
	}
	
	override suspend fun getLeaderboard() = dbFactory.dbQuery {
		UserTable.selectAll()
			.orderBy(UserTable.xp, SortOrder.DESC)
			.mapNotNull { Mapper.mapRowToLeaderboardResponse(it) }
	}
	
	override suspend fun addFavorite(uid: String, body: FavoriteBody) {
		dbFactory.dbQuery {
			FavoriteTable.insert { table ->
				table[FavoriteTable.uid] = uid
				table[menuId] = body.menuId
			}
		}
	}
	
	override suspend fun getAllFavoritesByUser(uid: String) = dbFactory.dbQuery {
		FavoriteTable.select {
			FavoriteTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToFavoriteResponse(it)
		}
	}
	
	override suspend fun addNewCoupon(body: CouponBody) {
		dbFactory.dbQuery {
			CouponTable.insert {
				it[couponId] = "COUPON ${NanoIdUtils.randomNanoId()}"
				it[imageUrl] = body.imageUrl
			}
		}
	}
	
	override suspend fun getAllCoupons() = dbFactory.dbQuery {
		CouponTable.selectAll().mapNotNull { Mapper.mapRowToCouponResponse(it) }
	}
	
	override suspend fun addNewMenu(body: MenuBody) {
		dbFactory.dbQuery {
			MenuTable.insert { table ->
				table[menuId] = "MENU ${NanoIdUtils.randomNanoId()}"
				table[benefit] = body.benefit
				table[category] = body.category
				table[description] = body.description
				table[difficulty] = body.difficulty
				table[calories] = body.calories
				table[cookTime] = body.cookTime
				table[estimatedTime] = body.estimatedTime
				table[image] = body.image
				table[ordered] = 0
				table[price] = body.price
				table[title] = body.title
				table[videoUrl] = body.videoUrl
			}
		}
	}
	
	override suspend fun getAllMenus() = dbFactory.dbQuery {
		
		MenuTable.join(ReviewTable, JoinType.LEFT) {
			MenuTable.menuId.eq(ReviewTable.menuId)
		}
			.slice(
				MenuTable.menuId,
				MenuTable.benefit,
				MenuTable.description,
				MenuTable.difficulty,
				MenuTable.calories,
				MenuTable.cookTime,
				MenuTable.estimatedTime,
				MenuTable.image,
				MenuTable.ordered,
				MenuTable.price,
				Avg(ReviewTable.rating, 1).alias("rating"),
				MenuTable.title,
				MenuTable.category,
				MenuTable.videoUrl
			)
			.selectAll()
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}
	
	override suspend fun getCategoryMenus(category: String) = dbFactory.dbQuery {
		MenuTable.join(ReviewTable, JoinType.LEFT) {
			MenuTable.menuId.eq(ReviewTable.menuId)
		}
			.slice(
				MenuTable.menuId,
				MenuTable.benefit,
				MenuTable.description,
				MenuTable.difficulty,
				MenuTable.calories,
				MenuTable.cookTime,
				MenuTable.estimatedTime,
				MenuTable.image,
				MenuTable.ordered,
				MenuTable.price,
				Avg(ReviewTable.rating, 1).alias("rating"),
				MenuTable.title,
				MenuTable.category,
				MenuTable.videoUrl
			)
			.select {
				MenuTable.category.eq(category)
			}
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}
	
	override suspend fun getDietMenus() = dbFactory.dbQuery {
		MenuTable.join(ReviewTable, JoinType.LEFT) {
			MenuTable.menuId.eq(ReviewTable.menuId)
		}
			.slice(
				MenuTable.menuId,
				MenuTable.benefit,
				MenuTable.description,
				MenuTable.difficulty,
				MenuTable.calories,
				MenuTable.cookTime,
				MenuTable.estimatedTime,
				MenuTable.image,
				MenuTable.ordered,
				MenuTable.price,
				Avg(ReviewTable.rating, 1).alias("rating"),
				MenuTable.title,
				MenuTable.category,
				MenuTable.videoUrl
			)
			.select {
				MenuTable.category.eq("Vegetable")
			}
			.groupBy(MenuTable.menuId)
			.orderBy(MenuTable.calories, SortOrder.ASC)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}
	
	override suspend fun getPopularMenus() = dbFactory.dbQuery {
		MenuTable.join(ReviewTable, JoinType.LEFT) {
			MenuTable.menuId.eq(ReviewTable.menuId)
		}
			.slice(
				MenuTable.menuId,
				MenuTable.benefit,
				MenuTable.description,
				MenuTable.difficulty,
				MenuTable.calories,
				MenuTable.cookTime,
				MenuTable.estimatedTime,
				MenuTable.image,
				MenuTable.ordered,
				MenuTable.price,
				Avg(ReviewTable.rating, 1).alias("rating"),
				MenuTable.title,
				MenuTable.category,
				MenuTable.videoUrl
			)
			.select {
				MenuTable.category.eq("Vegetable")
			}
			.groupBy(MenuTable.menuId)
			.orderBy(MenuTable.ordered, SortOrder.DESC)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}
	
	override suspend fun getExclusiveMenus() = dbFactory.dbQuery {
		MenuTable.join(ReviewTable, JoinType.LEFT) {
			MenuTable.menuId.eq(ReviewTable.menuId)
		}
			.slice(
				MenuTable.menuId,
				MenuTable.benefit,
				MenuTable.description,
				MenuTable.difficulty,
				MenuTable.calories,
				MenuTable.cookTime,
				MenuTable.estimatedTime,
				MenuTable.image,
				MenuTable.ordered,
				MenuTable.price,
				Avg(ReviewTable.rating, 1).alias("rating"),
				MenuTable.title,
				MenuTable.category,
				MenuTable.videoUrl
			)
			.select {
				MenuTable.category.eq("Vegetable")
			}
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}.shuffled()
	}
	
	override suspend fun getDetailMenu(menuId: String) = dbFactory.dbQuery {
		MenuTable.join(ReviewTable, JoinType.LEFT) {
			MenuTable.menuId.eq(ReviewTable.menuId)
		}
			.slice(
				MenuTable.menuId,
				MenuTable.benefit,
				MenuTable.description,
				MenuTable.difficulty,
				MenuTable.calories,
				MenuTable.cookTime,
				MenuTable.estimatedTime,
				MenuTable.image,
				MenuTable.ordered,
				MenuTable.price,
				Avg(ReviewTable.rating, 1).alias("rating"),
				MenuTable.title,
				MenuTable.category,
				MenuTable.videoUrl
			)
			.select {
				MenuTable.menuId.eq(menuId)
			}
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}.first()
	
	override suspend fun updateMenuOrder(menuId: String) {
		dbFactory.dbQuery {
			val totalOrderNow = MenuTable
				.select(MenuTable.menuId.eq(menuId))
				.firstNotNullOf { Mapper.mapMenuRowToInt(it) }
			
			MenuTable.update(
				where = {MenuTable.menuId.eq(menuId)}
			) { table ->
				table[ordered] = totalOrderNow.plus(1)
			}
		}
	}
	
	override suspend fun searchMenu(query: String) = dbFactory.dbQuery {
		MenuTable.join(ReviewTable, JoinType.LEFT) {
			MenuTable.menuId.eq(ReviewTable.menuId)
		}
			.slice(
				MenuTable.menuId,
				MenuTable.benefit,
				MenuTable.description,
				MenuTable.difficulty,
				MenuTable.calories,
				MenuTable.cookTime,
				MenuTable.estimatedTime,
				MenuTable.image,
				MenuTable.ordered,
				MenuTable.price,
				Avg(ReviewTable.rating, 1).alias("rating"),
				MenuTable.title,
				MenuTable.category,
				MenuTable.videoUrl
			)
			.select {
				LowerCase(MenuTable.title).like("%$query%".lowercase(Locale.getDefault()))
			}
			.groupBy(MenuTable.menuId)
			.mapNotNull {
				Mapper.mapRowToMenuResponse(it)
			}
	}
	
	override suspend fun addNewIngredient(body: IngredientBody) {
		dbFactory.dbQuery {
			IngredientTable.insert {
				it[menuId] = body.menuId
				it[ingredient] = body.ingredient
			}
		}
	}
	
	override suspend fun getIngredients(menuId: String) = dbFactory.dbQuery {
		IngredientTable.select {
			IngredientTable.menuId.eq(menuId)
		}.mapNotNull { Mapper.mapRowToIngredientResponse(it) }
	}
	
	override suspend fun addNewStep(body: StepBody) {
		dbFactory.dbQuery {
			StepTable.insert {
				it[menuId] = body.menuId
				it[step] = body.step
			}
		}
	}
	
	override suspend fun getSteps(menuId: String) = dbFactory.dbQuery {
		StepTable.select {
			StepTable.menuId.eq(menuId)
		}.mapNotNull { Mapper.mapRowToStepResponse(it) }
	}
	
	override suspend fun getReviews(menuId: String, request: ReviewRequest) = dbFactory.dbQuery {
		ReviewTable.join(UserTable, JoinType.INNER) {
			ReviewTable.uid.eq(request.uid)
		}.select {
			ReviewTable.menuId.eq(menuId)
		}.mapNotNull {
			Mapper.mapRowToReviewResponse(it)
		}
	}
	
	override suspend fun addNewVariant(body: VariantBody) {
		dbFactory.dbQuery {
			VariantTable.insert {
				it[menuId] = body.menuId
				it[composition] = body.composition
				it[image] = body.image
				it[price] = body.price
				it[variant] = body.variant
			}
		}
	}
	
	override suspend fun getAllVariants(menuId: String) = dbFactory.dbQuery {
		VariantTable.select {
			VariantTable.menuId.eq(menuId)
		}.mapNotNull {
			Mapper.mapRowToVariantResponse(it)
		}
	}
	
	override suspend fun getAllLastTransaction(uid: String) = dbFactory.dbQuery {
		HistoryTable.join(VariantTable, JoinType.INNER) {
			HistoryTable.menuId.eq(VariantTable.menuId) and HistoryTable.variant.eq(VariantTable.variant)
		}.select {
			HistoryTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToTransactionResponse(it)
		}
	}
	
	override suspend fun addNewHistory(uid: String, body: HistoryBody) {
		dbFactory.dbQuery {
			val dateObj = Date()
			val df: DateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm")
			df.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
			val dateCreated = df.format(dateObj)
			
			HistoryTable.insert {
				it[UserTable.uid] = uid
				it[menuId] = body.menuId
				it[transactionId] = "TRANSACTION ${NanoIdUtils.randomNanoId()}"
				it[timeStamp] = dateCreated
				it[variant] = body.variant
				it[status] = body.status
				it[starsGiven] = 0
			}
		}
	}
	
	override suspend fun updateHistoryStatus(body: HistoryUpdateBody) {
		dbFactory.dbQuery {
			HistoryTable.update(where = {HistoryTable.transactionId.eq(body.transactionId)}) {
				it[status] = body.status
			}
		}
	}
	
	override suspend fun updateHistoryStarsGiven(uid: String, body: HistoryUpdateStarsGiven) {
		dbFactory.dbQuery {
			HistoryTable.update(where = {HistoryTable.transactionId.eq(body.transactionId)}) {
				it[starsGiven] = body.starsGiven
			}
			
			ReviewTable.insert {
				it[ReviewTable.uid] = uid
				it[menuId] = body.menuId
				it[rating] = body.starsGiven.toDouble()
			}
		}
	}
	
	override suspend fun getAllHistoryByUser(uid: String) {
		dbFactory.dbQuery {
			HistoryTable.join(MenuTable, JoinType.INNER) {
				HistoryTable.menuId.eq(MenuTable.menuId)
			}.join(VariantTable, JoinType.INNER) {
				VariantTable.variant.eq(HistoryTable.variant)
			}.slice(
				HistoryTable.transactionId,
				MenuTable.menuId,
				MenuTable.image,
				MenuTable.title,
				HistoryTable.timeStamp,
				VariantTable.variant,
				HistoryTable.status,
				HistoryTable.starsGiven
			).select {
				HistoryTable.uid.eq(uid)
			}.mapNotNull {
				Mapper.mapRowToHistoryResponse(it)
			}
		}
	}
	
	override suspend fun addNewVoucher(body: VoucherBody) {
		dbFactory.dbQuery {
			VoucherTable.insert {
				it[voucherId] = "VOUCHER ${NanoIdUtils.randomNanoId()}"
				it[background] = body.background
				it[coinCost] = body.coinCost
			}
		}
	}
	
	override suspend fun getAvailableVoucher(uid: String) = dbFactory.dbQuery {
		VoucherTable.join(VoucherUserTable, JoinType.LEFT) {
			VoucherTable.voucherId.eq(VoucherUserTable.voucherId).and(VoucherUserTable.uid.eq(uid))
		}.select {
			VoucherUserTable.uid.isNull()
		}.map {
			Mapper.mapRowToVoucherResponse(it)
		}
	}
	
	override suspend fun getVoucherUser(uid: String) = dbFactory.dbQuery {
		VoucherUserTable.select {
			VoucherUserTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToVoucherUserResponse(it)
		}
	}
	
	override suspend fun getDetailVoucher(voucherId: String) = dbFactory.dbQuery {
		VoucherTable.select {
			VoucherTable.voucherId.eq(voucherId)
		}.mapNotNull {
			Mapper.mapRowToVoucherResponse(it)
		}
	}.first()
	
	override suspend fun getCaloriesPrediction(body: NoteBody): Int? {
		val menus = dbFactory.dbQuery {
			MenuTable.selectAll().mapNotNull { Mapper.mapRowToMenuResponse(it) }
		}
		
		val nbc = menus.toNaiveBayesClassifier(
			featuresSelector = { it.title.splitWords().toSet() },
			categorySelector = { it.calories }
		)
		
		val predictResult = nbc.predictWithProbability(body.food.splitWords().toSet())
		return predictResult?.category
	}
	
	override suspend fun addNewNote(body: NoteBody) {
		var caloriesPredict = 0
		CoroutineScope(Dispatchers.IO).launch {
			caloriesPredict = getCaloriesPrediction(body) ?: 0
		}.join()
		
		val dateObj = Date()
		val df: DateFormat = SimpleDateFormat("dd-MM-yyyy")
		df.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
		val dateCreated = df.format(dateObj)
		
		val tf = SimpleDateFormat("HH:mm")
		tf.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
		val timeCreated = tf.format(dateObj)
		
		dbFactory.dbQuery {
			NoteTable.insert { table ->
				table[uid] = body.uid
				table[noteId] = "NOTE ${NanoIdUtils.randomNanoId()}"
				table[category] = body.category
				table[calories] = caloriesPredict * body.portion
				table[date] = dateCreated
				table[food] = body.food
				table[information] = body.information
				table[portion] = body.portion
				table[time] = timeCreated
			}
		}
	}
	
	override suspend fun getAllNoteByUser(uid: String) = dbFactory.dbQuery {
		NoteTable.select {
			NoteTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToNoteResponse(it)
		}
	}
	
	override suspend fun addNewChallenge(body: ChallengeBody) {
		dbFactory.dbQuery {
			ChallengeTable.insert {
				it[challengeId] = "CHALLENGE ${NanoIdUtils.randomNanoId()}"
				it[title] = body.title
				it[description] = body.description
				it[participant] = body.participant
				it[xpEarned] = body.xpEarned
			}
		}
	}
	
	override suspend fun getAllAvailableChallenge() = dbFactory.dbQuery {
		ChallengeTable.selectAll().mapNotNull { Mapper.mapRowToChallengeResponse(it) }
	}
	
	private fun String.splitWords() =  split(Regex("\\s")).asSequence()
		.map { it.replace(Regex("[^A-Za-z]"),"").lowercase(Locale.getDefault()) }
		.filter { it.isNotEmpty() }
}