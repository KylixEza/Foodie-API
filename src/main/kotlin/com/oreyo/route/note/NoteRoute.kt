package com.oreyo.route.note

import com.oreyo.data.IFoodieRepository
import com.oreyo.model.note.NoteBody
import com.oreyo.route.RouteResponseHelper.generalException
import com.oreyo.route.RouteResponseHelper.generalListSuccess
import com.oreyo.route.RouteResponseHelper.generalSuccess
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.routing.*
import io.ktor.locations.post
import io.ktor.request.*

@KtorExperimentalLocationsAPI
class NoteRoute(
	private val repository: IFoodieRepository
) {
	
	private fun Route.postCaloriesPrediction() {
		post<NoteRouteLocation.CaloriesPredictionPostRoute> {
			val body = try {
				call.receive<NoteBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			call.generalSuccess { repository.getCaloriesPrediction(body) }
		}
	}
	
	private fun Route.postNewNote() {
		post<NoteRouteLocation.NotePostRoute> {
			val body = try {
				call.receive<NoteBody>()
			} catch (e: Exception) {
				call.generalException(e)
				return@post
			}
			call.generalSuccess { repository.addNewNote(body) }
		}
	}
	
	private fun Route.getNotesByUser() {
		get<NoteRouteLocation.NoteGetListRoute> {
			val uid = try {
				call.parameters["uid"]
			} catch (e: Exception) {
				call.generalException(e)
				return@get
			}
			call.generalListSuccess { repository.getAllNoteByUser(uid!!) }
		}
	}
	
	fun initNoteRoute(route: Route) {
		route.apply {
			postCaloriesPrediction()
			postNewNote()
			getNotesByUser()
		}
	}
}