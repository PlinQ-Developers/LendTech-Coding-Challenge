package apps.plinqdevelopers.lendtech.ui.loans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import apps.plinqdevelopers.lendtech.R
import apps.plinqdevelopers.lendtech.data.domain.DomainTransaction
import apps.plinqdevelopers.lendtech.databinding.LayoutTransactionItemBinding
import java.text.SimpleDateFormat
import java.util.*

class LoansAdapter  : ListAdapter<DomainTransaction, LoansAdapter.PaymentsViewHolder>(PaymentsComparator()) {
    inner class PaymentsViewHolder(private val binding : LayoutTransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction : DomainTransaction) {
            binding.apply {
                val type : String = transaction.transactionType
                if (type == "received") {
                    val name = transaction.transactionBank
                    val date : String = transaction.transactionDate
                    val timestamp : Long = date.toLong()
                    val formattedDate = getDateTime(time = timestamp)

                    //transactionsInitials.text = getNameInitials(name = name)
                    transactionName.text = "From: ${transaction.transactionFrom}"
                    transactionType.text = "Bank: ${transaction.transactionBank}"
                    transactionAmount.text = "+ ${transaction.transactionAmount} KES"
                    transactionDate.text = formattedDate

                    transactionAmount.setTextColor(R.color.appGreen)

                } else {
                    val name = transaction.transactionBank
                    val date: String = transaction.transactionDate
                    val timestamp: Long = date.toLong()
                    val formattedDate = getDateTime(time = timestamp)

                    //transactionsInitials.text = getNameInitials(name = name)
                    transactionName.text = "To: ${transaction.transactionTo}"
                    transactionType.text = "Mobile: ${transaction.transactionMobile}"
                    transactionAmount.text = "- ${transaction.transactionAmount} KES"
                    transactionDate.text = formattedDate

                    transactionAmount.setTextColor(R.color.appRed)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        return PaymentsViewHolder(
            LayoutTransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {
        val transactionItem : DomainTransaction = getItem(position)
        holder.bind(transaction = transactionItem)
    }

    class PaymentsComparator : DiffUtil.ItemCallback<DomainTransaction>() {
        override fun areItemsTheSame(
            oldItem: DomainTransaction,
            newItem: DomainTransaction
        ): Boolean {
            return oldItem.transactionID == newItem.transactionID
        }

        override fun areContentsTheSame(
            oldItem: DomainTransaction,
            newItem: DomainTransaction
        ): Boolean {
            return oldItem == newItem
        }
    }

    private fun getDateTime(time : Long): String? {
        return try {
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
            simpleDateFormat.format(time * 1000L)
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun getNameInitials(name : String?) : String {
        return if (name != null) {
            val splitName = name.split(" ")
            val firstName : String = splitName[0]

            firstName.get(1) + "" + firstName.get(2)
        } else {
            "O"
        }
    }
}