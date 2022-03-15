package com.oreyo.data

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.oreyo.data.database.DatabaseFactory
import com.oreyo.data.table.*
import com.oreyo.model.favorite.FavoriteBody
import com.oreyo.model.note.NoteBody
import com.oreyo.model.review.ReviewRequest
import com.oreyo.model.transaction.TransactionBody
import com.oreyo.model.user.UserBody
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
				table[uid] = NanoIdUtils.randomNanoId()
				table[address] = body.address
				table[avatar] = body.avatar
				table[coin] = body.coin
				table[foodieWallet] = body.foodieWallet
				table[email] = body.email
				table[name] = body.name
				table[phoneNumber] = body.phoneNumber
				table[xp] = body.xp
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
				table[coin] = body.coin
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
			.mapNotNull { it[UserTable.xp] }
	}
	
	override suspend fun addFavorite(body: FavoriteBody) {
		dbFactory.dbQuery {
			FavoriteTable.insert { table ->
				table[uid] = body.uid
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
	
	override suspend fun getAllCoupons() = dbFactory.dbQuery {
		CouponTable.selectAll().mapNotNull { Mapper.mapRowToCouponResponse(it) }
	}
	
	override suspend fun getAllMenus() = dbFactory.dbQuery {
		MenuTable.selectAll().mapNotNull { Mapper.mapRowToMenuResponse(it) }
	}
	
	override suspend fun getTypedMenus(type: String) = dbFactory.dbQuery {
		MenuTable.select {
			MenuTable.type.eq(type)
		}.map { Mapper.mapRowToMenuResponse(it) }
	}
	
	override suspend fun getDietMenus() = dbFactory.dbQuery {
		MenuTable.select {
			MenuTable.type.eq("Vegetable")
		}.orderBy(MenuTable.calories, SortOrder.DESC).mapNotNull {
			Mapper.mapRowToMenuResponse(it)
		}
	}
	
	override suspend fun updateMenuOrder(menuId: String) = dbFactory.dbQuery {
		val totalOrderNow = MenuTable
			.select(MenuTable.menuId.eq(menuId))
			.firstNotNullOf { Mapper.mapMenuRowToInt(it) }
		
		MenuTable.update(
			where = {MenuTable.menuId.eq(menuId)}
		) { table ->
			table[ordered] = totalOrderNow.plus(1)
		}
	}
	
	override suspend fun getPopularMenus() = dbFactory.dbQuery {
		MenuTable.selectAll().orderBy(MenuTable.ordered).mapNotNull {
			Mapper.mapRowToMenuResponse(it)
		}
	}
	
	override suspend fun getExclusiveMenus() = dbFactory.dbQuery {
		MenuTable.selectAll().mapNotNull { Mapper.mapRowToMenuResponse(it) }.shuffled()
	}
	
	override suspend fun getDetailMenu(menuId: String) = dbFactory.dbQuery {
		MenuTable.select {
			MenuTable.menuId.eq(menuId)
		}.mapNotNull { Mapper.mapRowToMenuResponse(it) }
	}.first()
	
	override suspend fun searchMenu(query: String) = dbFactory.dbQuery {
		MenuTable.select {
			LowerCase(MenuTable.title).like("%$query%".lowercase(Locale.getDefault()))
		}.mapNotNull { Mapper.mapRowToMenuResponse(it) }
	}
	
	override suspend fun getIngredients(menuId: String) = dbFactory.dbQuery {
		IngredientTable.select {
			IngredientTable.menuId.eq(menuId)
		}.mapNotNull { Mapper.mapRowToIngredientResponse(it) }
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
	
	override suspend fun getAllVariants(menuId: String) = dbFactory.dbQuery {
		VariantTable.select {
			VariantTable.menuId.eq(menuId)
		}.mapNotNull {
			Mapper.mapRowToVariantResponse(it)
		}
	}
	
	override suspend fun addNewTransaction(body: TransactionBody) {
		dbFactory.dbQuery {
			val dateObj = Date()
			val df: DateFormat = SimpleDateFormat("dd-MM-yyyy")
			df.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
			val dateCreated = df.format(dateObj)
			
			TransactionTable.insert { table ->
				table[uid] = body.uid
				table[transactionId] = "TRANSACTION ${NanoIdUtils.randomNanoId()}"
				table[variant] = body.variant
				table[date] = dateCreated
				table[price] = body.price
			}
		}
	}
	
	override suspend fun getAllTransaction(uid: String) = dbFactory.dbQuery {
		TransactionTable.select {
			TransactionTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToTransactionResponse(it)
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
	
	private fun String.splitWords() =  split(Regex("\\s")).asSequence()
		.map { it.replace(Regex("[^A-Za-z]"),"").lowercase(Locale.getDefault()) }
		.filter { it.isNotEmpty() }
}