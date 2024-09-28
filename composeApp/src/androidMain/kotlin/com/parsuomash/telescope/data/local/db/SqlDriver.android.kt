package com.parsuomash.telescope.data.local.db

//import app.cash.sqldelight.async.coroutines.synchronous
//import app.cash.sqldelight.db.QueryResult
//import app.cash.sqldelight.db.SqlDriver
//import app.cash.sqldelight.db.SqlSchema
//import app.cash.sqldelight.driver.android.AndroidSqliteDriver
//import com.parsuomash.telescope.di.applicationContext
//
//private class AndroidDriverFactory : DriverFactory {
//    override suspend fun createDriver(
//        schema: SqlSchema<QueryResult.AsyncValue<Unit>>
//    ): SqlDriver {
//        return AndroidSqliteDriver(schema.synchronous(), applicationContext, "telescope.db")
//    }
//}
//
//actual fun createDriverFactory(): DriverFactory = AndroidDriverFactory()
