package de.skash.timetrack.core.helper.fragment

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment


fun Fragment.openLinkInBrowser(link: String) {
    val webpage = Uri.parse(link)
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    startActivity(intent)
}