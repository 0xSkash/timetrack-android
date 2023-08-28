package de.skash.timetrack.core.cache.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class RealmSearchHistoryEntry(
    @PrimaryKey
    var title: String = ""
) : RealmObject {
    constructor(): this("")
}