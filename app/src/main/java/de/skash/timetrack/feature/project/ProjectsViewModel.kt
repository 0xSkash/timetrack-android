package de.skash.timetrack.feature.project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.skash.timetrack.core.helper.rx.toState
import de.skash.timetrack.core.helper.state.ErrorType
import de.skash.timetrack.core.helper.state.ResourceState
import de.skash.timetrack.core.model.SearchHistoryEntry
import de.skash.timetrack.core.repository.SearchHistoryRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {

    private val searchHistorySubject =
        BehaviorSubject.create<ResourceState<List<SearchHistoryEntry>>>()
    private val _searchHistoryLiveData = MutableLiveData<ResourceState<List<SearchHistoryEntry>>>()
    val searchHistoryLiveData: LiveData<ResourceState<List<SearchHistoryEntry>>> get() = _searchHistoryLiveData

    private val subscriptions = CompositeDisposable()

    init {
        searchHistorySubject
            .subscribe(_searchHistoryLiveData::postValue)
            .addTo(subscriptions)

        fetchSearchHistory()
    }

    fun fetchProjects(query: String = "") {

    }

    fun fetchSearchHistory(query: String = "") {
        searchHistoryRepository.fetchSearchHistoryWithQuery(query)
            .toState {
                ErrorType.SearchQueryFetchFailed
            }
            .subscribe {
                searchHistorySubject.onNext(it)
            }
            .addTo(subscriptions)
    }

    fun cacheSearchHistoryEntry(query: String) {
        if (query.isEmpty()) {
            return
        }

        searchHistoryRepository.createSearchHistoryEntry(SearchHistoryEntry(query))
            .doOnNext {
                fetchSearchHistory()
            }
            .toState {
                ErrorType.SearchQueryCreationFailed
            }
            .subscribe {
                Log.d(javaClass.name, "cacheSearchHistoryEntry state :: $it")
            }
            .addTo(subscriptions)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}