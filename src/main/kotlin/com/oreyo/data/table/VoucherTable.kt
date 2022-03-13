package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object VoucherTable: Table() {

    override val tableName: String = "voucher"

    val voucherId = varchar("voucher_id", 512)
    val background = varchar("background", 512)
    val coinCost = integer("coin_cost")
    val validUntil = varchar("valid_until", 512)
    val voucherCategory = varchar("voucher_category", 512)
    val voucherDiscount = integer("voucher_discount")

    override val primaryKey: PrimaryKey = PrimaryKey(voucherId)
}