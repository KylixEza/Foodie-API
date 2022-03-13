package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object MenuTable: Table() {

    override val tableName: String = "menu"

    val menuId = varchar("menuId", 512)
    val difficulty = varchar("difficulty", 512)
    val calories = integer("calories")
    val image = varchar("image", 512)
    val price = integer("price")
    val rating = double("rating")
    val estimatedTime = integer("estimated_time")
    val title = varchar("title", 512)
    val type = varchar("type", 512)

    override val primaryKey: PrimaryKey = PrimaryKey(menuId)
}