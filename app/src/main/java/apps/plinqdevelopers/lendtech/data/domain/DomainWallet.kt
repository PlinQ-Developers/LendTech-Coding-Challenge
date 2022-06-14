package apps.plinqdevelopers.lendtech.data.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "walletsTable")
data class DomainWallet(

    @PrimaryKey
    val walletID : String,

    val accountID : String,

    val balance : String

) : Parcelable
