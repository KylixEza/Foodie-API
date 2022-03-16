package com.oreyo.route

import com.oreyo.model.GeneralListResponse
import com.oreyo.model.GeneralResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object RouteResponseHelper {
	
	suspend inline fun<reified T> ApplicationCall.generalSuccess(noinline action: suspend () -> T) = flow {
		try {
			val data = action()
			emit(
				this@generalSuccess.respond(
					HttpStatusCode.OK,
					GeneralResponse(
						HttpStatusCode.OK.value.toString(),
						"Success",
						if (data is Unit) "Nothing" else data
					)
				)
			)
		} catch (e: Exception) {
			emit(this@generalSuccess.generalException(e))
		}
	}.flowOn(Dispatchers.IO)
	
	suspend inline fun ApplicationCall.generalException(exception: Exception) {
		when(exception) {
			is BadRequestException -> this.respond(HttpStatusCode.BadRequest, GeneralResponse(HttpStatusCode.BadRequest.value.toString(), exception.message.toString(), null))
			is NotFoundException -> this.respond(HttpStatusCode.NotFound, GeneralResponse(HttpStatusCode.NotFound.value.toString(), exception.message.toString(), null))
			else -> this.respond(HttpStatusCode.Conflict, GeneralResponse(HttpStatusCode.Conflict.value.toString(), exception.message.toString(), null))
		}
	}
	
	suspend inline fun<reified T> ApplicationCall.generalListSuccess(noinline action: suspend () -> T) = flow {
		try {
			val count = count { action() as List<*> }
			emit(
				this@generalListSuccess.respond(
					HttpStatusCode.OK,
					GeneralListResponse(
						HttpStatusCode.OK.value.toString(),
						"Request Success",
						count,
						action()
					)
				)
			)
		} catch (e: Exception) {
			emit(this@generalListSuccess.listNoteException(e))
		}
	}.flowOn(Dispatchers.IO)
	
	suspend inline fun ApplicationCall.listNoteException(e: Exception) {
		val listResponse = GeneralListResponse(message = e.message.toString(), count = 0, data = arrayListOf<Any>())
		when(e) {
			is BadRequestException -> {
				listResponse.status = HttpStatusCode.BadRequest.value.toString()
				this.respond(
					HttpStatusCode.BadRequest,
					listResponse
				)
			}
			is NotFoundException -> {
				listResponse.status = HttpStatusCode.NotFound.value.toString()
				this.respond(
					HttpStatusCode.NotFound,
					listResponse
				)
			}
			else -> {
				listResponse.status = HttpStatusCode.Conflict.value.toString()
				this.respond(
					HttpStatusCode.NotFound,
					listResponse
				)
			}
		}
	}
	
	inline fun count(block: () -> List<*>) = block().size
}