package de.skash.timetrack.feature.timer

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import de.skash.timetrack.R
import de.skash.timetrack.databinding.FragmentTimerBinding
import de.skash.timetrack.feature.adapter.TimerFragmentStateAdapter

class TimerFragment : Fragment(R.layout.fragment_timer) {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    private val notificationRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTimerBinding.bind(view)

        val adapter = TimerFragmentStateAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setText(adapter.getTitleForPosition(position))
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}