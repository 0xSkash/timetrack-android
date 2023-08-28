package de.skash.timetrack.core.repository

import de.skash.timetrack.core.model.SearchHistoryEntry
import io.reactivex.rxjava3.core.Observable

interface SearchHistoryRepository {

    fun fetchSearchHistoryWithQuery(query: String): Observable<List<SearchHistoryEntry>>
    fun createSearchHistoryEntry(entry: SearchHistoryEntry): Observable<Unit>
}