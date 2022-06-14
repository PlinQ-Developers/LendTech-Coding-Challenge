package apps.plinqdevelopers.lendtech.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiValidation(

    @SerializedName("status")
    @Expose
    val status : Boolean

)
