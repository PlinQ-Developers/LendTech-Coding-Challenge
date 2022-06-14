package apps.plinqdevelopers.lendtech.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiWallet(

    @Expose
    @SerializedName("_id")
    val walletID : String,

    @Expose
    @SerializedName("accountID")
    val accountID : String,

    @Expose
    @SerializedName("balance")
    val balance : String
)
