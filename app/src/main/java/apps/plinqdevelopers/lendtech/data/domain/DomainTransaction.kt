package apps.plinqdevelopers.lendtech.data.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "transactionsTable")
data class DomainTransaction(

    @PrimaryKey
    val transactionID : String,

    val accountID : String,

    val transactionFrom : String?,

    val transactionTo : String?,

    val transactionDate : String,

    val transactionAmount : String,

    val transactionBank : String?,

    val transactionMobile : String,

    val transactionType : String

) : Parcelable
