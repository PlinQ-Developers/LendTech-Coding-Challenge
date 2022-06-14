package apps.plinqdevelopers.lendtech.api

import apps.plinqdevelopers.lendtech.data.network.ApiAccount
import apps.plinqdevelopers.lendtech.data.network.ApiTransaction
import apps.plinqdevelopers.lendtech.data.network.ApiValidation
import apps.plinqdevelopers.lendtech.data.network.ApiWallet
import apps.plinqdevelopers.lendtech.data.payload.PayloadAccount
import apps.plinqdevelopers.lendtech.data.payload.PayloadRequest
import retrofit2.http.*

interface ApplicationAPI {

    @POST("lend/auth/validate/email")
    suspend fun isEmailActive(
        @Body payload : PayloadAccount
    ) : ApiValidation

    @POST("lend/auth/login")
    suspend fun appLogin(
        @Body payload : PayloadAccount
    ) : ApiAccount

    @POST("lend/auth/register")
    suspend fun appRegister(
        @Body payload : PayloadAccount
    ) : ApiAccount


    @POST("lend/wallet")
    suspend fun getWallet(
        @Header("Authorization") authToken : String,
        @Body paramID : PayloadRequest
    ) : ApiWallet


    @POST("lend/transactions")
    suspend fun getTransactions(
        @Header("Authorization") authToken : String,
        @Body paramID : PayloadRequest
    ) : List<ApiTransaction>
}