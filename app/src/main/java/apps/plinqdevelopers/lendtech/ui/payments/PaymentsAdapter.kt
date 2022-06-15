package apps.plinqdevelopers.lendtech.ui.payments

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

class PaymentsAdapter : ListAdapter<DomainTransaction, PaymentsAdapter.PaymentsViewHolder> (PaymentsComparator()) {
    inner class PaymentsViewHolder(private val binding : LayoutTransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction : DomainTransaction) {
            binding.apply {
                transactionAmount.setTextColor(R.color.appRed)

                val name = transaction.transactionTo
                val date: String = transaction.transactionDate.toString()
                val timestamp  = date.split(" ")
                val formattedDate = timestamp[1] + ", " + timestamp[2] + " " + timestamp[5]

                transactionsInitials.text = getNameInitials(name = name)
                transactionName.text = "To: ${transaction.transactionTo}"
                transactionType.text = "Mobile: ${transaction.transactionMobile}"
                transactionAmount.text = "- ${transaction.transactionAmount} KES"
                transactionDate.text = formattedDate
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

    private fun getDateTime(time : Long): String {
        return try {
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            simpleDateFormat.format(time * 1000L)
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun getNameInitials(name : String?) : String {
        return if (name != null) {
            val splitName = name.split(" ")
            val firstName : String = splitName[0]
            val lastName : String = splitName[1]

            firstName.get(0) + "" + lastName.get(0)
        } else {
            "O"
        }
    }
}