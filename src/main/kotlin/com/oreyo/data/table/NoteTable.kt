package com.oreyo.data.table

import org.jetbrains.exposed.sql.Table

object NoteTable: Table() {
	
	override val tableName: String = "note"
	
	val uid = reference("uid", UserTable.uid)
	val noteId = varchar("note_id", 512)
	val category = varchar("category", 512)
	val calories = integer("calories")
	val date = varchar("date", 512)
	val food = varchar("food", 512)
	val information = varchar("information", 512)
	val portion = integer("portion")
	val time = varchar("time", 512)
	
	override val primaryKey: PrimaryKey = PrimaryKey(noteId)
}