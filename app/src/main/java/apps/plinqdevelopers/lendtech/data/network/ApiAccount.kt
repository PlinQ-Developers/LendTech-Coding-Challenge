package apps.plinqdevelopers.lendtech.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiAccount(

    @Expose
    @SerializedName("_id")
    val authID : String,

    @Expose
    @SerializedName("authName")
    val authName : String,

    @Expose
    @SerializedName("authEmail")
    val authEmail : String,

    @Expose
    @SerializedName("authPassword")
    val authPassword : String,

    @Expose
    @SerializedName("authToken")
    val authToken : String,

    @Expose
    @SerializedName("accountProfile")
    val authProfile : String

)
