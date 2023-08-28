package de.skash.timetrack.feature.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.skash.timetrack.core.helper.rx.toState
import de.skash.timetrack.core.helper.state.ErrorType
import de.skash.timetrack.core.helper.state.ResourceState
import de.skash.timetrack.core.model.AuthData
import de.skash.timetrack.core.repository.AuthRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val loginStateSubject = PublishSubject.create<ResourceState<AuthData>>()
    private val _loginStateLiveData = MutableLiveData<ResourceState<AuthData>>()
    val loginStateLiveData: LiveData<ResourceState<AuthData>> get() = _loginStateLiveData

    private val subscriptions = CompositeDisposable()

    init {
        loginStateSubject
            .subscribe(_loginStateLiveData::postValue)
            .addTo(subscriptions)
    }

    fun login(email: String, password: String) {
        authRepository.login(email, password)
            .toState {
                ErrorType.UserUnauthenticated
            }
            .subscribe {
                loginStateSubject.onNext(it)
            }
            .addTo(subscriptions)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }
}