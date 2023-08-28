package de.skash.timetrack.feature.adapter

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import de.skash.timetrack.R
import de.skash.timetrack.feature.timer.task.TaskFragment
import de.skash.timetrack.feature.timer.worktime.WorkTimeFragment

class TimerFragmentStateAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TaskFragment()
            1 -> WorkTimeFragment()
            else -> throw IllegalArgumentException("Tried to create fragment for invalid position. Valid positions are 0 - ${itemCount - 1}")
        }
    }

    @StringRes
    fun getTitleForPosition(position: Int): Int {
        return when (position) {
            0 -> R.string.title_task
            1 -> R.string.title_work_time
            else -> throw IllegalArgumentException("Tried to get title for invalid position. Valid positions are 0 - ${itemCount - 1}")
        }
    }
}