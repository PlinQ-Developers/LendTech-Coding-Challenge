package apps.plinqdevelopers.lendtech.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiTransaction(

    @Expose
    @SerializedName("_id")
    val transactionID : String,

    @Expose
    @SerializedName("accountID")
    val accountID : String,

    @Expose
    @SerializedName("from")
    val transactionFrom : String?,

    @Expose
    @SerializedName("to")
    val transactionTo : String?,

    @Expose
    @SerializedName("date")
    val transactionDate : String,

    @Expose
    @SerializedName("amount")
    val transactionAmount : String,

    @Expose
    @SerializedName("bank")
    val transactionBank : String?,

    @Expose
    @SerializedName("mobile")
    val transactionMobile : String,

    @Expose
    @SerializedName("type")
    val transactionType : String

)
