package de.skash.timetrack.feature.auth.registration

import android.os.Bundle
import android.view.View
import de.skash.timetrack.R
import de.skash.timetrack.core.constants.Constants
import de.skash.timetrack.core.helper.fragment.openLinkInBrowser
import de.skash.timetrack.databinding.FragmentRegistrationBinding
import de.skash.timetrack.feature.dialog.FullscreenDialogFragment

class RegistrationFragment : FullscreenDialogFragment(R.layout.fragment_registration) {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRegistrationBinding.bind(view)
        bindActions()
    }

    private fun bindActions() {
        binding.toolbar.setNavigationOnClickListener {
            dismiss()
        }

        binding.termsTextView.setOnClickListener {
            openLinkInBrowser(Constants.LINK_TERMS_OF_USE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}