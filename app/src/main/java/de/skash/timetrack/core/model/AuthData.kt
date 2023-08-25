package de.skash.timetrack.core.model

data class AuthData(
    val bearerToken: String,
    val user: User
)