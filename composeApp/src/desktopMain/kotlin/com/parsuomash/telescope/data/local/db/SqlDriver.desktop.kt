package com.parsuomash.telescope.data.local.db

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

private class DesktopDriverFactory : DriverFactory {
    override suspend fun createDriver(
        schema: SqlSchema<QueryResult.AsyncValue<Unit>>
    ): SqlDriver {
        return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            .also { schema.create(it).await() }
    }
}

actual fun createDriverFactory(): DriverFactory = DesktopDriverFactory()
