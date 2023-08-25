package de.skash.timetrack.feature.auth

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

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    private val loadingDialog: LoadingDialog by loadingDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindActions()

        viewModel.loginStateLiveData.observe(this) { loginState ->
            loginState.handle(this, loadingDialog, onSuccess = {
                getPrefs().saveUserToken(it.bearerToken)
                getPrefs().saveSelfUser(it.user)
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

        binding.forgotPasswordButton.setOnClickListener {
            //TODO: Present Password reset progress
        }
    }
}