package de.skash.timetrack.core.repository

import de.skash.timetrack.core.model.AuthData
import io.reactivex.rxjava3.core.Observable

interface AuthRepository {

    fun login(email: String, password: String): Observable<AuthData>
}