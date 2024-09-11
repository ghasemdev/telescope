package com.parsumash.treehouse

import app.cash.zipline.ZiplineService
import kotlin.native.ObjCName

@ObjCName("HostApi", exact = true)
interface HostApi : ZiplineService {
    /** Decodes the response as a string and returns it. */
    suspend fun httpCall(url: String, headers: Map<String, String>): String

    fun openUrl(url: String)
}
