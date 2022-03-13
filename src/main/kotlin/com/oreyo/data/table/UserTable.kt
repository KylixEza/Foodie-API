package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {

    override val tableName: String = "user"

    val uid = varchar("uid", 512)
    val address = varchar("address", 512)
    val avatar = varchar("avatar", 1024)
    val coin = integer("coin")
    val foodieWallet = integer("foodie_wallet")
    val email = varchar("email", 512)
    val name = varchar("name", 512)
    val phoneNumber = varchar("phone_number", 512)
    val xp = integer("xp")

    override val primaryKey: PrimaryKey = PrimaryKey(uid)
}