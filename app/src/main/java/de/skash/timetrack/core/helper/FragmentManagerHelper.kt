package de.skash.timetrack.core.helper

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment

fun FragmentManager.findNavHostFragment(@IdRes fragmentId: Int): NavHostFragment {
    return findFragmentById(fragmentId) as NavHostFragment
}