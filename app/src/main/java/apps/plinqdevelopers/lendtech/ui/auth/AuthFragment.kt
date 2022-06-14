package apps.plinqdevelopers.lendtech.ui.auth

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import apps.plinqdevelopers.lendtech.R
import apps.plinqdevelopers.lendtech.data.domain.DomainAccount
import apps.plinqdevelopers.lendtech.data.domain.DomainPreferences
import apps.plinqdevelopers.lendtech.data.payload.PayloadAccount
import apps.plinqdevelopers.lendtech.databinding.FragmentAuthBinding
import apps.plinqdevelopers.lendtech.utils.ResponseType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val authViewModel : AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            closeErrorButton.setOnClickListener {
                errorContainer.visibility = View.GONE
            }
            validateEmailButton.setOnClickListener {
                val emailAddress : String = authEmail.text.toString()
                if (!TextUtils.isEmpty(emailAddress)) {
                    validateEmailButton.visibility = View.GONE
                    loadingProgress.visibility = View.VISIBLE
                    validateLoginEmail(payload = PayloadAccount(
                        authEmail = emailAddress,
                        authPassword = null,
                        authName = null
                    )
                    )
                } else {
                    authEmail.error = "Email field is required!"
                }
            }

            loginButton.setOnClickListener {
                val loginEmail : String = authEmail.text.toString()
                val loginPassword : String = authPassword.text.toString()
                if (!TextUtils.isEmpty(loginEmail) || !TextUtils.isEmpty(loginPassword)) {
                    loginButton.visibility = View.GONE
                    loadingProgress.visibility = View.VISIBLE
                    accountLogin(payload = PayloadAccount(
                        authEmail = loginEmail,
                        authPassword = loginPassword,
                        authName = null
                    )
                    )
                } else {
                    authError.text = "All fields are required!"
                    errorContainer.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun validateLoginEmail(payload : PayloadAccount) {
        binding.apply {
            authViewModel.isEmailRegistered(payload = payload).observe(requireActivity(), Observer { emailResults ->
                when(emailResults) {
                    is ResponseType.Loading -> {

                    }
                    is ResponseType.Error -> {
                        validateEmailButton.visibility = View.VISIBLE
                        loadingProgress.visibility = View.GONE
                        authError.text = "Error: ${emailResults.error?.localizedMessage}"
                        errorContainer.visibility = View.VISIBLE
                    }
                    is ResponseType.Success -> {
                        loadingProgress.visibility = View.GONE
                        val response : Boolean? = emailResults.data?.status
                        if (response != null) {
                            if (response) {
                                emailValidationContainer.visibility = View.GONE
                                loginPasswordContainer.visibility = View.VISIBLE
                                loginButtonContainer.visibility = View.VISIBLE
                            } else {
                                validateEmailButton.visibility = View.VISIBLE
                                authError.text = "Error authenticating your account. Please try again later."
                                errorContainer.visibility = View.VISIBLE
                            }
                        } else {
                            validateEmailButton.visibility = View.VISIBLE
                            authError.text = "Error authenticating your account. Please try again later."
                            errorContainer.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }


    private fun accountLogin(payload : PayloadAccount) {
        binding.apply {
            authViewModel.accountLogin(payload = payload).observe(requireActivity(), Observer { loginResponse ->
                when(loginResponse) {
                    is ResponseType.Loading -> {

                    }
                    is ResponseType.Error -> {
                        loginButton.visibility = View.VISIBLE
                        loadingProgress.visibility = View.GONE
                        authError.text = "Error: ${loginResponse.error?.localizedMessage}"
                        errorContainer.visibility = View.VISIBLE
                    }
                    is ResponseType.Success -> {
                        loadingProgress.visibility = View.GONE
                        val account : DomainAccount? = loginResponse.data
                        if (account != null) {
                            val domainPreferences : DomainPreferences = DomainPreferences(
                                authID = account.authID,
                                authName = account.authName,
                                authEmail = account.authEmail,
                                authToken = account.authToken,
                                authStatus = true
                            )
                            lifecycleScope.launch {
                                authViewModel.saveNewPreferences(prefs = domainPreferences)
                                authViewModel.updateAuthenticationStatus(status = true)

                                val gotoHome = AuthFragmentDirections.actionAuthFragmentToHomeFragment()
                                findNavController().navigate(gotoHome)
                            }
                        } else {
                            authError.text = "Error authenticating your account. Please try again later."
                            errorContainer.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}