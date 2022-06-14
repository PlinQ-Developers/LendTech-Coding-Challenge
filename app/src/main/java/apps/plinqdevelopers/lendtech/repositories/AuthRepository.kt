package apps.plinqdevelopers.lendtech.repositories

import apps.plinqdevelopers.lendtech.api.ApplicationAPI
import apps.plinqdevelopers.lendtech.data.mappers.AccountMapper
import apps.plinqdevelopers.lendtech.data.network.ApiValidation
import apps.plinqdevelopers.lendtech.data.payload.PayloadAccount
import apps.plinqdevelopers.lendtech.room.ApplicationDAO
import apps.plinqdevelopers.lendtech.room.ApplicationDatabase
import apps.plinqdevelopers.lendtech.utils.ResponseType
import apps.plinqdevelopers.lendtech.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository
@Inject
constructor(
    private val applicationAPI: ApplicationAPI,
    private val mapper : AccountMapper,
    private val applicationDatabase: ApplicationDatabase
){
    private val applicationDAO : ApplicationDAO = applicationDatabase.applicationDAO()

    fun isEmailRegistered(payload: PayloadAccount) : Flow<ResponseType<ApiValidation>> = flow {
        emit(ResponseType.Loading(data = null))
        try {
            emit(
                value = ResponseType.Success(
                    data = applicationAPI.isEmailActive(payload = payload
                    )
                )
            )
        } catch (e: Throwable) {
            emit(ResponseType.Error(data = null, throwable = e))
        }
    }


    fun accountLogin(payload : PayloadAccount) = networkBoundResource(
        query = {
            applicationDAO.getAccountDetails()
        },
        fetch = {
            applicationAPI.appLogin(payload = payload)
        },
        saveFetchedResults = { apiAccount ->
            applicationDAO.manageAccountsTable(
                account = mapper.mapToDomain(data = apiAccount)
            )
        }
    )


    fun accountRegistration(payload : PayloadAccount) = networkBoundResource(
        query = {
            applicationDAO.getAccountDetails()
        },
        fetch = {
            applicationAPI.appRegister(payload = payload)
        },
        saveFetchedResults = { apiAccount ->
            applicationDAO.manageAccountsTable(
                account = mapper.mapToDomain(data = apiAccount)
            )
        }
    )
}