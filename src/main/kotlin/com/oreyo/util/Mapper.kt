package com.oreyo.util

import com.oreyo.data.table.*
import com.oreyo.model.favorite.FavoriteResponse
import com.oreyo.model.ingredient.IngredientResponse
import com.oreyo.model.menu.MenuResponse
import com.oreyo.model.review.ReviewResponse
import com.oreyo.model.step.StepResponse
import com.oreyo.model.transaction.TransactionResponse
import com.oreyo.model.user.UserResponse
import com.oreyo.model.variant.VariantResponse
import com.oreyo.model.voucher.VoucherResponse
import com.oreyo.model.voucher_user.VoucherUserResponse
import org.jetbrains.exposed.sql.ResultRow

object Mapper {
	
	fun mapRowToUserResponse(row: ResultRow?): UserResponse? {
		if (row == null)
			return null
		
		return UserResponse(
			uid = row[UserTable.uid],
			address = row[UserTable.address],
			avatar = row[UserTable.avatar],
			coin = row[UserTable.coin],
			foodieWallet = row[UserTable.foodieWallet],
			email = row[UserTable.email],
			name = row[UserTable.name],
			phoneNumber = row[UserTable.phoneNumber],
			xp = row[UserTable.xp]
		)
	}
	
	fun mapRowToMenuResponse(row: ResultRow?): MenuResponse? {
		if (row == null)
			return null
		
		return MenuResponse(
			menuId = row[MenuTable.menuId],
			benefit = row[MenuTable.benefit],
			description = row[MenuTable.description],
			difficulty = row[MenuTable.difficulty],
			calories = row[MenuTable.calories],
			cookTime = row[MenuTable.cookTime],
			estimatedTime = row[MenuTable.estimatedTime],
			image = row[MenuTable.image],
			price = row[MenuTable.price],
			rating = row[MenuTable.rating],
			title = row[MenuTable.title],
			type = row[MenuTable.type],
			videoUrl = row[MenuTable.videoUrl]
		)
	}
	
	fun mapRowToIngredientResponse(row: ResultRow?): IngredientResponse? {
		if (row == null)
			return null
		
		return IngredientResponse(
			menuId = row[IngredientTable.menuId],
			ingredient = row[IngredientTable.ingredient]
		)
	}
	
	fun mapRowToStepResponse(row: ResultRow?): StepResponse? {
		if (row == null)
			return null
		
		return StepResponse(
			menuId = row[StepTable.menuId],
			step = row[StepTable.step]
		)
	}
	
	fun mapRowToReviewResponse(row: ResultRow?): ReviewResponse? {
		if (row == null)
			return null
		
		return ReviewResponse(
			menuId = row[ReviewTable.menuId],
			name = row[UserTable.name],
			avatar = row[UserTable.avatar],
			rating = row[ReviewTable.rating],
		)
	}
	
	fun mapRowToVariantResponse(row: ResultRow?): VariantResponse? {
		return if(row == null)
			null
		else {
			VariantResponse(
				menuId = row[VariantTable.menuId],
				composition = row[VariantTable.composition],
				image = row[VariantTable.image],
				price = row[VariantTable.price],
				variant = row[VariantTable.variant]
			)
		}
	}
	
	fun mapRowToFavoriteResponse(row: ResultRow?): FavoriteResponse? {
		if(row == null)
			return null
		
		return FavoriteResponse(
			uid = row[FavoriteTable.uid],
			menuId = row[FavoriteTable.menuId]
		)
	}
	
	fun mapRowToTransactionResponse(row: ResultRow?): TransactionResponse? {
		if (row == null)
			return null
		
		return TransactionResponse(
			uid = row[TransactionTable.transactionId],
			transactionId = row[TransactionTable.transactionId],
			variant = row[TransactionTable.variant],
			date = row[TransactionTable.date],
			price = row[TransactionTable.price]
		)
	}
	
	fun mapRowToVoucherResponse(row: ResultRow): VoucherResponse {
		return VoucherResponse(
			voucherId = row[VoucherTable.voucherId],
			background = row[VoucherTable.background],
			coinCost = row[VoucherTable.coinCost],
			validUntil = row[VoucherTable.validUntil],
			voucherCategory = row[VoucherTable.voucherCategory],
			voucherDiscount = row[VoucherTable.voucherDiscount]
		)
	}
	
	fun mapRowToVoucherUserResponse(row: ResultRow): VoucherUserResponse {
		return VoucherUserResponse(
			uid = row[VoucherUserTable.uid],
			voucherId = row[VoucherUserTable.voucherId]
		)
	}
	
	fun mapMenuRowToInt(row: ResultRow): Int = row[MenuTable.ordered]
}