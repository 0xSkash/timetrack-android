package de.skash.timetrack.core.helper.state

import android.content.Context
import de.skash.timetrack.core.helper.dialog.showErrorAlert
import de.skash.timetrack.core.helper.state.loading.LoadingDialog


sealed class ResourceState<T> {
    class Success<T>(val value: T) : ResourceState<T>()
    class Loading<T> : ResourceState<T>()
    class Error<T>(val errorType: ErrorType) : ResourceState<T>()

    fun valueOrNull(): T? {
        if (this is Success) {
            return this.value
        }
        return null
    }
}

inline fun <T> ResourceState<T>.handle(
    context: Context,
    loadingDialog: LoadingDialog?,
    onSuccess: (T) -> Unit,
    // Return false of you did not handle the error and wishes to let the function handle the error
    handleError: (ErrorType) -> Boolean = { false },
) {
    when (this) {
        is ResourceState.Error -> {
            loadingDialog?.dismiss()

            if (handleError(errorType)) {
                return
            }
            context.showErrorAlert(this.errorType)
        }

        is ResourceState.Loading -> loadingDialog?.show()
        is ResourceState.Success -> {
            loadingDialog?.dismiss()
            onSuccess(this.value)
        }
    }
}