package apps.plinqdevelopers.lendtech.room

import androidx.room.*
import apps.plinqdevelopers.lendtech.data.domain.DomainAccount
import apps.plinqdevelopers.lendtech.data.domain.DomainTransaction
import apps.plinqdevelopers.lendtech.data.domain.DomainWallet
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationDAO {

    /**
     * Accounts table queries
     */
    @Query("SELECT * FROM accountsTable")
    fun getAccountDetails() : Flow<DomainAccount>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAccountsTable(account : DomainAccount)

    @Query("DELETE FROM accountsTable")
    suspend fun deleteAccountRecords()

    @Transaction
    suspend fun manageAccountsTable(account: DomainAccount) {
        deleteAccountRecords()
        updateAccountsTable(account = account)
    }


    /**
     * Wallet table queries
     */
    @Query("SELECT * FROM walletsTable")
    fun getWalletDetails() : Flow<DomainWallet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWalletTable(wallet : DomainWallet)

    @Query("DELETE FROM walletsTable")
    suspend fun deleteWalletRecord()

    @Transaction
    suspend fun manageWalletTable(wallet: DomainWallet) {
        deleteWalletRecord()
        updateWalletTable(wallet = wallet)
    }


    /**
     * Transactions table queries
     */
    @Query("SELECT * FROM transactionsTable")
    fun getAllTransactions() : Flow<List<DomainTransaction>>

    @Query("SELECT * FROM transactionsTable WHERE transactionType = :filterType")
    fun getFilteredTransactions(filterType : String) : Flow<List<DomainTransaction>>

    @Query("SELECT * FROM transactionsTable WHERE transactionDate <= :startDate AND transactionDate >= :endDate")
    fun getSortedTransactions(startDate : String, endDate : String) : List<DomainTransaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransactionsTable(transactions : List<DomainTransaction>)

    @Query("DELETE FROM transactionsTable")
    suspend fun deleteTransactionRecords()

    @Transaction
    suspend fun manageTransactionsTable(transactions: List<DomainTransaction>) {
        deleteTransactionRecords()
        updateTransactionsTable(transactions = transactions)
    }

}