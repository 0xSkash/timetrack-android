package de.skash.timetrack.feature.auth.reset

import android.os.Bundle
import android.view.View
import de.skash.timetrack.R
import de.skash.timetrack.databinding.FragmentRequestPasswordResetBinding
import de.skash.timetrack.feature.dialog.FullscreenDialogFragment

class RequestPasswordResetFragment :
    FullscreenDialogFragment(R.layout.fragment_request_password_reset) {

    private var _binding: FragmentRequestPasswordResetBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRequestPasswordResetBinding.bind(view)
        bindActions()
    }

    private fun bindActions() {
        binding.toolbar.setNavigationOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}