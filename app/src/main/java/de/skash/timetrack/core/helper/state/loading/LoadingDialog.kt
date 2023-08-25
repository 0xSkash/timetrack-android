package de.skash.timetrack.core.helper.state.loading

import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface LoadingDialog {
    fun show()
    fun dismiss()
}

enum class LoadingDialogStyle {
    DEFAULT
}

class LoadingDialogDelegate<T : LoadingDialog>(
    private val style: LoadingDialogStyle,
    private val initializer: (Context) -> T
) : ReadOnlyProperty<LifecycleOwner, T> {

    private var value: T? = null

    override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): T {
        if (value == null) {
            value = initializer(getContextFromOwner(thisRef))
        }
        return value!!
    }

    private fun getContextFromOwner(owner: LifecycleOwner): Context {
        return when (owner) {
            is FragmentActivity -> owner
            is Fragment -> owner.requireContext()
            is ContextWrapper -> owner.baseContext
            else -> throw IllegalArgumentException("Unsupported LifecycleOwner type")
        }
    }
}

fun loadingDialog(style: LoadingDialogStyle = LoadingDialogStyle.DEFAULT): LoadingDialogDelegate<LoadingDialog> =
    LoadingDialogDelegate(style) { context ->
        when (style) {
            LoadingDialogStyle.DEFAULT -> DefaultLoadingDialog(context)
        }
    }
