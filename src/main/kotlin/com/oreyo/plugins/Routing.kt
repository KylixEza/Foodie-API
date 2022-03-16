package com.oreyo.plugins

import com.oreyo.route.challenge.ChallengeRoute
import com.oreyo.route.menu.MenuRoute
import com.oreyo.route.menu.MenuRouteLocation
import com.oreyo.route.note.NoteRoute
import com.oreyo.route.user.UserRoute
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.ktor.ext.inject

@KtorExperimentalLocationsAPI
fun Application.configureRouting() {

    val challengeRoute by inject<ChallengeRoute>()
    val menuRoute by inject<MenuRoute>()
    val noteRoute by inject<NoteRoute>()
    val userRoute by inject<UserRoute>()
    
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        
        challengeRoute.initChallengeRoute(this)
        menuRoute.initMenuRoute(this)
        noteRoute.initNoteRoute(this)
        userRoute.initUserRoute(this)
    }
}
