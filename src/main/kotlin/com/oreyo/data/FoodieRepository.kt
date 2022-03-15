package com.oreyo.data

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.oreyo.data.database.DatabaseFactory
import com.oreyo.data.table.*
import com.oreyo.model.favorite.FavoriteBody
import com.oreyo.model.menu.MenuBody
import com.oreyo.model.note.NoteBody
import com.oreyo.model.transaction.TransactionBody
import com.oreyo.model.user.UserBody
import com.oreyo.util.Mapper
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.nield.kotlinstatistics.toNaiveBayesClassifier
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FoodieRepository(
	private val dbFactory: DatabaseFactory
) {
	
	suspend fun addUser(body: UserBody) {
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
	
	suspend fun getDetailUser(uid: String) = dbFactory.dbQuery {
		UserTable.select {
			UserTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToUserResponse(it)
		}
	}.first()
	
	suspend fun updateUser(uid: String, body: UserBody) = dbFactory.dbQuery {
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
	
	suspend fun getLeaderboard() = dbFactory.dbQuery {
		UserTable.selectAll()
			.orderBy(UserTable.xp, SortOrder.DESC)
			.mapNotNull { it[UserTable.xp] }
	}
	
	suspend fun addFavorite(body: FavoriteBody) = dbFactory.dbQuery {
		FavoriteTable.insert { table ->
			table[uid] = body.uid
			table[menuId] = body.menuId
		}
	}
	
	suspend fun getAllFavoritesByUser(uid: String) = dbFactory.dbQuery {
		FavoriteTable.select {
			FavoriteTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToFavoriteResponse(it)
		}
	}
	
	suspend fun getAllMenus() = dbFactory.dbQuery {
		MenuTable.selectAll().mapNotNull { Mapper.mapRowToMenuResponse(it) }
	}
	
	suspend fun getTypedMenus(type: String) = dbFactory.dbQuery {
		MenuTable.select {
			MenuTable.type.eq(type)
		}.map { Mapper.mapRowToMenuResponse(it) }
	}
	
	suspend fun getDietMenus() = dbFactory.dbQuery {
		MenuTable.select {
			MenuTable.type.eq("Vegetable")
		}.orderBy(MenuTable.calories, SortOrder.DESC).mapNotNull {
			Mapper.mapRowToMenuResponse(it)
		}
	}
	
	suspend fun updateMenuOrder(menuId: String, body: MenuBody) = dbFactory.dbQuery {
		val totalOrderNow = MenuTable
			.select(MenuTable.menuId.eq(menuId))
			.firstNotNullOf { Mapper.mapMenuRowToInt(it) }
		
		MenuTable.update(
			where = {MenuTable.menuId.eq(menuId)}
		) { table ->
			table[MenuTable.ordered] = totalOrderNow.plus(1)
		}
	}
	
	suspend fun getPopularMenus() = dbFactory.dbQuery {
		MenuTable.selectAll().orderBy(MenuTable.ordered).mapNotNull {
			Mapper.mapRowToMenuResponse(it)
		}
	}
	
	suspend fun getExclusiveMenus() = dbFactory.dbQuery {
		MenuTable.selectAll().mapNotNull { Mapper.mapRowToMenuResponse(it) }.shuffled()
	}
	
	suspend fun getDetailMenu(menuId: String) = dbFactory.dbQuery {
		MenuTable.select {
			MenuTable.menuId.eq(menuId)
		}.mapNotNull { Mapper.mapRowToMenuResponse(it) }
	}.first()
	
	suspend fun searchMenu(query: String) = dbFactory.dbQuery {
		MenuTable.select {
			LowerCase(MenuTable.title).like("%$query%".lowercase(Locale.getDefault()))
		}.mapNotNull { Mapper.mapRowToMenuResponse(it) }
	}
	
	suspend fun getIngredients(menuId: String) = dbFactory.dbQuery {
		IngredientTable.select {
			IngredientTable.menuId.eq(menuId)
		}.mapNotNull { Mapper.mapRowToIngredientResponse(it) }
	}
	
	suspend fun getSteps(menuId: String) = dbFactory.dbQuery {
		StepTable.select {
			StepTable.menuId.eq(menuId)
		}.mapNotNull { Mapper.mapRowToStepResponse(it) }
	}
	
	suspend fun getReviews(menuId: String) = dbFactory.dbQuery {
		ReviewTable.join(UserTable, JoinType.INNER) {
			ReviewTable.uid.eq(menuId)
		}.select {
			ReviewTable.menuId.eq(menuId)
		}.mapNotNull {
			Mapper.mapRowToReviewResponse(it)
		}
	}
	
	suspend fun getAllVariants(menuId: String) = dbFactory.dbQuery {
		VariantTable.select {
			VariantTable.menuId.eq(menuId)
		}.mapNotNull {
			Mapper.mapRowToVariantResponse(it)
		}
	}
	
	suspend fun addNewTransaction(body: TransactionBody) = dbFactory.dbQuery {
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
	
	suspend fun getAllTransaction(uid: String) = dbFactory.dbQuery {
		TransactionTable.select {
			TransactionTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToTransactionResponse(it)
		}
	}
	
	suspend fun getAvailableVoucher(uid: String) = dbFactory.dbQuery {
		VoucherTable.join(VoucherUserTable, JoinType.LEFT) {
			VoucherTable.voucherId.eq(VoucherUserTable.voucherId).and(VoucherUserTable.uid.eq(uid))
		}.select {
			VoucherUserTable.uid.isNull()
		}.map {
			Mapper.mapRowToVoucherResponse(it)
		}
	}
	
	suspend fun getVoucherUser(uid: String) = dbFactory.dbQuery {
		VoucherUserTable.select {
			VoucherUserTable.uid.eq(uid)
		}.mapNotNull {
			Mapper.mapRowToVoucherUserResponse(it)
		}
	}
	
	suspend fun getDetailVoucher(voucherId: String) = dbFactory.dbQuery {
		VoucherTable.select {
			VoucherUserTable.voucherId.eq(voucherId)
		}.mapNotNull {
			Mapper.mapRowToVoucherResponse(it)
		}
	}.first()
	
	suspend fun getCaloriesPrediction(body: NoteBody): Int? {
		val menus = dbFactory.dbQuery {
			MenuTable.selectAll().mapNotNull { Mapper.mapRowToMenuResponse(it) }
		}
		
		val nbc = menus.toNaiveBayesClassifier(
			featuresSelector = { it.title.splitWords().toSet()},
			categorySelector = { it.calories }
		)
		
		val predictResult = nbc.predictWithProbability(body.food.splitWords().toSet())
		return predictResult?.category
	}
	
	private fun String.splitWords() =  split(Regex("\\s")).asSequence()
		.map { it.replace(Regex("[^A-Za-z]"),"").lowercase(Locale.getDefault()) }
		.filter { it.isNotEmpty() }
}