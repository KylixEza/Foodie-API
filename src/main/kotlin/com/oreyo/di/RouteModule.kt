package com.oreyo.di

import com.oreyo.route.challenge.ChallengeRoute
import com.oreyo.route.menu.MenuRoute
import com.oreyo.route.note.NoteRoute
import com.oreyo.route.user.UserRoute
import io.ktor.locations.*
import org.koin.dsl.module

@KtorExperimentalLocationsAPI
val routeModule = module {
	factory { ChallengeRoute(get()) }
	factory { MenuRoute(get()) }
	factory { NoteRoute(get()) }
	factory { UserRoute(get()) }
}