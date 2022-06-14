package apps.plinqdevelopers.lendtech.ui.loans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import apps.plinqdevelopers.lendtech.databinding.FragmentLoansBinding
import apps.plinqdevelopers.lendtech.utils.ResponseType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoansFragment : Fragment() {
    private var _binding: FragmentLoansBinding? = null
    private val binding get() = _binding!!

    private val loansViewModel : LoansViewModel by viewModels()
    private val loansAdapter : LoansAdapter  = LoansAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loadLoanTransactions()

            paymentTransactionsList.apply {
                hasFixedSize()
                adapter = loansAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun loadLoanTransactions() {
        binding.apply {
            loansViewModel.getPaymentTransactions(filter = "received").observe(requireActivity(), Observer { dataList ->
                loansAdapter.submitList(dataList)
            })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}