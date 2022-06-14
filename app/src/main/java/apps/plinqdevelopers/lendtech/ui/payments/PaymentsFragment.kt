package apps.plinqdevelopers.lendtech.ui.payments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import apps.plinqdevelopers.lendtech.R
import apps.plinqdevelopers.lendtech.databinding.FragmentPaymentsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaymentsFragment : Fragment() {
    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!

    private val paymentsViewModel : PaymentsViewModel by viewModels()
    private val paymentsAdapter : PaymentsAdapter = PaymentsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loadLoanTransactions()

            paymentTransactionsList.apply {
                hasFixedSize()
                adapter = paymentsAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun loadLoanTransactions() {
        binding.apply {
            paymentsViewModel.getPaymentTransactions(filter = "sent").observe(requireActivity(), Observer { dataList ->
                paymentsAdapter.submitList(dataList)
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}