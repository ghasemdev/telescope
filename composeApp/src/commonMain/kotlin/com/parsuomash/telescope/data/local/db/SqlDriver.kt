package com.parsuomash.telescope.data.local.db

//import app.cash.sqldelight.db.QueryResult
//import app.cash.sqldelight.db.SqlDriver
//import app.cash.sqldelight.db.SqlSchema
//
//interface DriverFactory {
//    suspend fun createDriver(
//        schema: SqlSchema<QueryResult.AsyncValue<Unit>>
//    ): SqlDriver
//}
//
//expect fun createDriverFactory(): DriverFactory

//fun createDatabase(driverFactory: DriverFactory): Database {
//    val driver = driverFactory.createDriver()
//    val database = Database(driver)
//
//    // Do more work with the database (see below).
//}
