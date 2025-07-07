package com.sakhura.disqueramp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform