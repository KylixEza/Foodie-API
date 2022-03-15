package com.oreyo.route.note

import com.oreyo.route.user.UserRouteLocation.Companion.SELECTED_USER

sealed class NoteRouteLocation {
	companion object {
		const val NOTE = "/note"
		const val CALORIES_PREDICTION = "/calories/predict"
		//POST
		const val ADD_NOTE = NOTE
		//GET
		const val GET_NOTE = "$SELECTED_USER/note"
	}
}
