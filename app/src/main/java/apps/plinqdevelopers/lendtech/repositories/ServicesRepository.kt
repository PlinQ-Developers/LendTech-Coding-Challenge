package apps.plinqdevelopers.lendtech.repositories

import apps.plinqdevelopers.lendtech.api.ApplicationAPI
import apps.plinqdevelopers.lendtech.data.domain.DomainTransaction
import apps.plinqdevelopers.lendtech.data.mappers.TransactionMapper
import apps.plinqdevelopers.lendtech.data.mappers.WalletMapper
import apps.plinqdevelopers.lendtech.data.payload.PayloadRequest
import apps.plinqdevelopers.lendtech.room.ApplicationDAO
import apps.plinqdevelopers.lendtech.room.ApplicationDatabase
import apps.plinqdevelopers.lendtech.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServicesRepository
@Inject
constructor(
    private val applicationAPI : ApplicationAPI,
    private val applicationDatabase : ApplicationDatabase,
    private val walletsMapper : WalletMapper,
    private val transactionsMapper : TransactionMapper
){
    private val applicationDAO : ApplicationDAO = applicationDatabase.applicationDAO()

    fun loadTransactions(paramID : PayloadRequest, authToken : String) = networkBoundResource(
        query = {
            applicationDAO.getAllTransactions()
        },
        fetch = {
            val bearerToken : String = "Bearer $authToken"
            applicationAPI.getTransactions(paramID = paramID, authToken = bearerToken)
        },
        saveFetchedResults = { apiTransactionsList ->
            applicationDAO.manageTransactionsTable(
                transactions = transactionsMapper.mapToDomainList(list = apiTransactionsList)
            )
        }
    )


    fun loadWallet(paramID : PayloadRequest, authToken : String) = networkBoundResource(
        query = {
            applicationDAO.getWalletDetails()
        },
        fetch = {
            val bearerToken : String = "Bearer $authToken"
            applicationAPI.getWallet(paramID = paramID, authToken = bearerToken)
        },
        saveFetchedResults = { apiWallet ->
            applicationDAO.manageWalletTable(
                wallet = walletsMapper.mapToDomain(data = apiWallet)
            )
        }
    )

    fun getFilteredTransactions(filterType : String) : Flow<List<DomainTransaction>> {
        return applicationDAO.getFilteredTransactions(filterType = filterType)
    }

}

