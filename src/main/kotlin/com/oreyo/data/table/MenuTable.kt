package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object MenuTable: Table() {

    override val tableName: String = "menu"

    val menuId = varchar("menuId", 512)
    val benefit = varchar("benefit", 2048)
    val description = varchar("description", 2048)
    val difficulty = varchar("difficulty", 512)
    val calories = integer("calories")
    val cookTime = integer("cook_time")
    val estimatedTime = varchar("estimated_time", 512)
    val image = varchar("image", 512)
    val ordered = integer("ordered")
    val price = integer("price")
    val rating = double("rating")
    val title = varchar("title", 512)
    val type = varchar("type", 512)
    val videoUrl = varchar("video_url", 512)

    override val primaryKey: PrimaryKey = PrimaryKey(menuId)
}