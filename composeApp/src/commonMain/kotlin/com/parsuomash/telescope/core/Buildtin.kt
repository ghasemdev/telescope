package com.parsuomash.telescope.core

inline fun tryIgnore(block: () -> Unit) {
    try {
        block()
    } catch (_: Throwable) {
    }
}

inline fun tryOrEmpty(block: () -> String): String {
    return try {
        block()
    } catch (_: Throwable) {
        ""
    }
}

inline fun <T> tryOrDefault(block: () -> T, default: T): T {
    return try {
        block()
    } catch (_: Throwable) {
        default
    }
}

inline fun <T> tryOrNull(block: () -> T): T? {
    return try {
        block()
    } catch (_: Throwable) {
        null
    }
}

inline fun <T> tryOrElse(block: () -> T, `else`: () -> T): T {
    return try {
        block()
    } catch (_: Throwable) {
        `else`()
    }
}
