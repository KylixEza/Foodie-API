package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object TransactionTable: Table() {

    override val tableName: String = "transaction"

    val uid = reference("uid", UserTable.uid)
    val transactionId = varchar("transaction_id", 512)
    val variant = varchar("variant", 512)
    val date = varchar("date", 512)
    val price = integer("price")

    override val primaryKey: PrimaryKey = PrimaryKey(transactionId)
}