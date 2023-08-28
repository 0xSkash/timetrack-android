package de.skash.timetrack.feature.auth.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import de.skash.timetrack.core.helper.prefs.getPrefs
import de.skash.timetrack.core.helper.prefs.saveSelfUser
import de.skash.timetrack.core.helper.prefs.saveUserToken
import de.skash.timetrack.core.helper.state.handle
import de.skash.timetrack.core.helper.state.loading.LoadingDialog
import de.skash.timetrack.core.helper.state.loading.loadingDialog
import de.skash.timetrack.MainActivity
import de.skash.timetrack.databinding.ActivityLoginBinding
import de.skash.timetrack.feature.auth.registration.RegistrationFragment
import de.skash.timetrack.feature.auth.reset.RequestPasswordResetFragment
import de.skash.timetrack.feature.workspace.WorkspaceSelectionBottomSheet

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    private val loadingDialog: LoadingDialog by loadingDialog()

    private val workspaceSelectionBottomSheet: WorkspaceSelectionBottomSheet by lazy {
        WorkspaceSelectionBottomSheet()
    }

    private val requestPasswordResetFragment: RequestPasswordResetFragment by lazy {
        RequestPasswordResetFragment()
    }

    private val signUpFragment: RegistrationFragment by lazy {
        RegistrationFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindActions()

        viewModel.loginStateLiveData.observe(this) { loginState ->
            loginState.handle(this, loadingDialog, onSuccess = { authData ->
                getPrefs().saveUserToken(authData.bearerToken)
                getPrefs().saveSelfUser(authData.user)

                if (authData.user.defaultWorkspaceId == null) {
                    workspaceSelectionBottomSheet.show(supportFragmentManager, null)
                    return@observe
                }

                MainActivity.launch(this)
                finish()
            })
        }
    }

    private fun bindActions() {
        binding.signInButton.setOnClickListener {
            viewModel.login(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.signUpButton.setOnClickListener {
            signUpFragment.show(supportFragmentManager, null)
        }

        binding.forgotPasswordButton.setOnClickListener {
            requestPasswordResetFragment.show(supportFragmentManager, null)
        }
    }
}