package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object TransactionTable: Table() {

    override val tableName: String = "transaction"

    val uid = reference("uid", UserTable.uid)
    val transactionId = varchar("transaction_id", 128)
    val variant = varchar("variant", 128)
    val date = varchar("date", 48)
    val price = integer("price")

    override val primaryKey: PrimaryKey = PrimaryKey(transactionId)
}