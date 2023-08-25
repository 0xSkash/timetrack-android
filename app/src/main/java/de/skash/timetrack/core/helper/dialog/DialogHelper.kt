package de.skash.timetrack.core.helper.dialog

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.skash.timetrack.core.helper.state.ErrorType
import de.skash.timetrack.R

fun Context.showErrorAlert(errorType: ErrorType) {
    MaterialAlertDialogBuilder(this)
        .setMessage(errorType.errorMessage)
        .setPositiveButton(R.string.error_alert_ok_button_title, null)
        .show()
}