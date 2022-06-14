package apps.plinqdevelopers.lendtech.data.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "accountsTable")
@Parcelize
data class DomainAccount(

    @PrimaryKey
    val authID : String,

    val authName : String,

    val authEmail : String,

    val authPassword : String,

    val authToken : String,

    val authProfile : String

) : Parcelable
