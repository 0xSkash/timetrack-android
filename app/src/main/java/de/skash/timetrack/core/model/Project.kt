package de.skash.timetrack.core.model

import java.util.UUID

data class Project(
    val id: UUID,
    val title: String,
    val color: String
)