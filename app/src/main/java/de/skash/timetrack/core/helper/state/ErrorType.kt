package de.skash.timetrack.core.helper.state

import androidx.annotation.StringRes
import de.skash.timetrack.R

sealed class ErrorType(@StringRes val errorMessage: Int) {

    companion object {
        private const val ERROR_CODE_TWO_FA_MISSING = 449
        /*
        fun fromThrowable(throwable: HttpException): ErrorType? {
            return when (throwable.code()) {
                else -> null
            }
        }

         */
    }

    data object UserUnauthenticated : ErrorType(R.string.error_type_user_unauthorized)
    data object SearchQueryFetchFailed : ErrorType(R.string.error_type_search_history_fetch)
    data object SearchQueryCreationFailed : ErrorType(R.string.error_type_search_history_creation)

}