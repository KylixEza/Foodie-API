package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object VariantTable: Table() {

    override val tableName: String = "variant"

    val menuId = reference("menu_id", MenuTable.menuId)
    val composition = varchar("composition", 512)
    val image = varchar("image", 512)
    val price = integer("price")
    val variant = varchar("variant", 512)

}