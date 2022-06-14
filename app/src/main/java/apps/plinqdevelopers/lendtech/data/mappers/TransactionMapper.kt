package apps.plinqdevelopers.lendtech.data.mappers

import apps.plinqdevelopers.lendtech.data.domain.DomainTransaction
import apps.plinqdevelopers.lendtech.data.network.ApiTransaction
import apps.plinqdevelopers.lendtech.data.utils.DataMapper
import javax.inject.Inject

class TransactionMapper @Inject constructor()  : DataMapper<DomainTransaction, ApiTransaction> {
    override fun mapToDomain(data: ApiTransaction): DomainTransaction {
        return DomainTransaction(
            transactionAmount = data.transactionAmount,
            transactionBank = data.transactionBank,
            transactionDate = data.transactionDate,
            transactionFrom = data.transactionFrom,
            transactionID = data.transactionID,
            transactionMobile = data.transactionMobile,
            transactionTo = data.transactionTo,
            transactionType = data.transactionType,
            accountID = data.accountID
        )
    }

    override fun mapToApi(data: DomainTransaction): ApiTransaction {
        return ApiTransaction(
            transactionAmount = data.transactionAmount,
            transactionBank = data.transactionBank,
            transactionDate = data.transactionDate,
            transactionFrom = data.transactionFrom,
            transactionID = data.transactionID,
            transactionMobile = data.transactionMobile,
            transactionTo = data.transactionTo,
            transactionType = data.transactionType,
            accountID = data.accountID
        )
    }

    fun mapToDomainList(list : List<ApiTransaction>) : List<DomainTransaction> {
        return list.map { mapToDomain(data = it) }
    }
}