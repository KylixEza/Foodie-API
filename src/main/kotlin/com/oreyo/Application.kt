package com.oreyo

import com.oreyo.di.databaseModule
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.oreyo.plugins.*
import io.ktor.application.*
import org.koin.core.logger.Level
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt(), host = "localhost") {
        install(Koin) {
            slf4jLogger(Level.ERROR)
            modules(listOf(databaseModule))
        }
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
