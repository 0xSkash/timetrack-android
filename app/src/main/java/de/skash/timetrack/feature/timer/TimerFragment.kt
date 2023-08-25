package de.skash.timetrack.feature.timer

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import de.skash.timetrack.R
import de.skash.timetrack.databinding.FragmentTimerBinding
import de.skash.timetrack.feature.service.TimeTrackService
import de.skash.timetrack.feature.service.WorkTimeTrackService

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationRequestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        binding.startTimer.setOnClickListener {
            val intent = Intent(requireContext(), WorkTimeTrackService::class.java).apply {
                putExtra(TimeTrackService.TIMER_ACTION, TimeTrackService.START_TIMER)
            }

            requireActivity().startForegroundService(intent)
        }

        binding.stopTimer.setOnClickListener {
            val intent = Intent(requireContext(), WorkTimeTrackService::class.java).apply {
                putExtra(TimeTrackService.TIMER_ACTION, TimeTrackService.STOP_TIMER)
            }

            requireActivity().startService(intent)
        }

        binding.backgroundTimer.setOnClickListener {
            val intent = Intent(requireContext(), WorkTimeTrackService::class.java).apply {
                putExtra(TimeTrackService.TIMER_ACTION, TimeTrackService.MOVE_TIMER_TO_BACKGROUND)
            }

            requireActivity().startService(intent)
        }

        binding.foregroundTimer.setOnClickListener {
            val intent = Intent(requireContext(), WorkTimeTrackService::class.java).apply {
                putExtra(TimeTrackService.TIMER_ACTION, TimeTrackService.MOVE_TIMER_TO_FOREGROUND)
            }

            requireActivity().startService(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}