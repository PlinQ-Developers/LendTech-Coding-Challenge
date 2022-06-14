package apps.plinqdevelopers.lendtech.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import apps.plinqdevelopers.lendtech.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val splashViewModel : SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                lifecycleScope.launch {
                    splashViewModel.appPreferences.collect { prefs ->
                        val authStatus : Boolean = prefs.authStatus

                        if (authStatus) {
                            val gotoHome = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                            findNavController().navigate(gotoHome)
                        } else {
                            val gotoLogin = SplashFragmentDirections.actionSplashFragmentToAuthFragment()
                            findNavController().navigate(gotoLogin)
                        }
                    }
                }
            }
        }
        timer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}