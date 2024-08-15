package com.parsuomash.telescope.data.local.db

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver

private class IOSDriverFactory : DriverFactory {
    override suspend fun createDriver(
        schema: SqlSchema<QueryResult.AsyncValue<Unit>>
    ): SqlDriver {
        return NativeSqliteDriver(schema.synchronous(), "telescope.db")
    }
}

actual fun createDriverFactory(): DriverFactory = IOSDriverFactory()
