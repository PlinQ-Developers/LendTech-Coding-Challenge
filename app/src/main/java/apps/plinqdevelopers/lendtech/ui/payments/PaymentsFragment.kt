package apps.plinqdevelopers.lendtech.ui.payments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import apps.plinqdevelopers.lendtech.R
import apps.plinqdevelopers.lendtech.databinding.FragmentPaymentsBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


@AndroidEntryPoint
class PaymentsFragment : Fragment() {
    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!

    private val paymentsViewModel : PaymentsViewModel by viewModels()
    private val paymentsAdapter : PaymentsAdapter = PaymentsAdapter()

    private var dateFrom : Long = 1L
    private var dateTo : Long = 1L

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
            onSelectStart()
            onSelectEnd()
            loadLoanTransactions()

            paymentTransactionsList.apply {
                hasFixedSize()
                adapter = paymentsAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            filterButton.setOnClickListener {
                if (!TextUtils.isEmpty(from.text.toString()) || !TextUtils.isEmpty(to.text.toString())) {
                    val filterFrom : Long = dateFrom
                    val filterTo : Long = dateTo

                    paymentsViewModel.filterDates(from = filterFrom, to = filterTo, filter = "sent").observe(requireActivity(), Observer { filteredList ->
                        from.text?.clear()
                        to.text?.clear()
                        paymentsAdapter.submitList(filteredList)
                        if (filteredList.isEmpty()) {
                            listErrorText.text = "Your filter sequence has 0 matches. Please try a different filter. Thank you"
                            listErrorText.visibility = View.VISIBLE
                        }
                    })
                } else {
                    listErrorText.visibility = View.GONE
                    loadLoanTransactions()
                    Toast.makeText(requireContext(), "All date fields are required for filtering!", Toast.LENGTH_LONG).show()
                }
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

    private fun onSelectStart() {
        binding.apply {
            from.setOnClickListener {
                val datePickers =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build()
                datePickers.show(requireActivity().supportFragmentManager, "Date Picker Dialog")
                datePickers.addOnPositiveButtonClickListener { selectedDate ->
                    dateFrom = selectedDate
                    from.setText(getDateTime(selectedDate))
                }
            }
        }
    }

    private fun onSelectEnd() {
        binding.apply {
            to.setOnClickListener {
                val datePickers =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build()
                datePickers.show(requireActivity().supportFragmentManager, "Date Picker Dialog")
                datePickers.addOnPositiveButtonClickListener { selectedDate ->
                    dateTo = selectedDate
                    to.setText(getDateTime(selectedDate))
                }
            }
        }
    }

    private fun getDateTime(s: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        return simpleDateFormat.format(Date(s))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}