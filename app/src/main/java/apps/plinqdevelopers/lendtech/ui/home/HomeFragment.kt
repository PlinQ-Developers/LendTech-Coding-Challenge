package apps.plinqdevelopers.lendtech.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import apps.plinqdevelopers.lendtech.R
import apps.plinqdevelopers.lendtech.data.domain.DomainTransaction
import apps.plinqdevelopers.lendtech.data.domain.DomainWallet
import apps.plinqdevelopers.lendtech.data.payload.PayloadRequest
import apps.plinqdevelopers.lendtech.databinding.FragmentHomeBinding
import apps.plinqdevelopers.lendtech.utils.ResponseType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel : HomeViewModel by viewModels()
    private val homeAdapter : HomeAdapter = HomeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleScope.launch {
                homeViewModel.appPreferences.collect { prefs ->
                    val authToken : String = prefs.authEmail
                    val authID : String = prefs.authID

                    val payload : PayloadRequest = PayloadRequest(paramID = authID)

                    loadWalletDetails(authID = payload, authToken = authToken, name = prefs.authName.split(" ")[0])
                    loadLatestTransactions(authID = payload, authToken = authToken)
                }
            }

            homeTransactionsList.apply {
                hasFixedSize()
                adapter = homeAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            actionPayments.setOnClickListener {
                val gotoPayments = HomeFragmentDirections.actionHomeFragmentToPaymentsFragment()
                findNavController().navigate(gotoPayments)
            }

            actionLoans.setOnClickListener {
                val gotoLoans = HomeFragmentDirections.actionHomeFragmentToLoansFragment()
                findNavController().navigate(gotoLoans)
            }
        }
    }

    private fun loadWalletDetails(authID : PayloadRequest, authToken: String, name : String) {
        binding.apply {
            homeViewModel.loadWallet(paramID = authID, authToken = authToken).observe(requireActivity(), Observer { dataList ->
                val walletDetails = dataList.data
                if (walletDetails != null) {
                    walletAmount.text = walletDetails.balance
                    val profileName : String = "Hello $name"
                    walletName.text = profileName
                }
            })
        }
    }

    private fun loadLatestTransactions(authID : PayloadRequest, authToken: String) {
        binding.apply {
            homeViewModel.getLatestTransactions(paramID = authID, authToken = authToken).observe(requireActivity(), Observer { dataList ->
                homeAdapter.submitList(dataList.data?.take(5))

                loadingProgress.isVisible = dataList is ResponseType.Loading && dataList.data.isNullOrEmpty()
                errorText.isVisible = dataList is ResponseType.Error && dataList.data.isNullOrEmpty()
                errorText.text = dataList.error?.localizedMessage
            })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}