package de.skash.timetrack.core.repository

import de.skash.timetrack.core.model.AuthData
import de.skash.timetrack.core.model.User
import io.reactivex.rxjava3.core.Observable
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ApiAuthRepository @Inject constructor() : AuthRepository {

    override fun login(email: String, password: String): Observable<AuthData> {
        return Observable.just(AuthData("EWEWEGWEG", User(UUID.randomUUID(), email)))
            .delay(5000, TimeUnit.MILLISECONDS)
    }
}