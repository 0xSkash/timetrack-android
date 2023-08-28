package de.skash.timetrack.core.repository

import de.skash.timetrack.core.cache.model.RealmSearchHistoryEntry
import de.skash.timetrack.core.model.SearchHistoryEntry
import io.reactivex.rxjava3.core.Observable
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.rx3.asObservable
import kotlinx.coroutines.rx3.rxObservable

class RealmSearchHistoryRepository : SearchHistoryRepository {
    override fun fetchSearchHistoryWithQuery(query: String): Observable<List<SearchHistoryEntry>> {
        val config = RealmConfiguration.create(schema = setOf(RealmSearchHistoryEntry::class))
        val realm: Realm = Realm.open(config)

        val query = if (query.isEmpty()) {
            realm.query(RealmSearchHistoryEntry::class)
        } else {
            realm.query(RealmSearchHistoryEntry::class, "title BEGINSWITH $0", query)
        }
            .limit(20)
            .find()
            .asFlow()
            .map {
                it.list.map { entry ->
                    SearchHistoryEntry(entry.title)
                }
            }
            .onCompletion {
                realm.close()
            }

        return query.asObservable()
            .take(1)
    }

    override fun createSearchHistoryEntry(entry: SearchHistoryEntry): Observable<Unit> {
        val config = RealmConfiguration.create(schema = setOf(RealmSearchHistoryEntry::class))
        val realm: Realm = Realm.open(config)

        return rxObservable<Unit> {
            realm.write {
                this.copyToRealm(RealmSearchHistoryEntry(title = entry.title), UpdatePolicy.ALL)
            }
        }.doOnNext {
            realm.close()
        }
    }
}