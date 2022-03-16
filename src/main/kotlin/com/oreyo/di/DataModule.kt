package com.oreyo.di

import com.oreyo.data.FoodieRepository
import com.oreyo.data.IFoodieRepository
import com.oreyo.data.database.DatabaseFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.koin.dsl.module

val databaseModule = module {
	single {
		DatabaseFactory(get())
	}
	
	factory {
		val config = HikariConfig()
		config.apply {
			driverClassName = System.getenv("JDBC_DRIVER")
			jdbcUrl = System.getenv("DATABASE_URL")
			maximumPoolSize = 6
			isAutoCommit = false
			transactionIsolation = "TRANSACTION_REPEATABLE_READ"
			
			/*val uri = URI(System.getenv("DATABASE_URL"))
			val username = uri.userInfo.split(":").toTypedArray()[0]
			val password = uri.userInfo.split(":").toTypedArray()[1]
			jdbcUrl =
				"jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path + "?sslmode=require" + "&user=$username&password=$password"*/
			
			validate()
			
		}
		HikariDataSource(config)
	}
}

val repositoryModule = module {
	single<IFoodieRepository> {
		FoodieRepository(get())
	}
}