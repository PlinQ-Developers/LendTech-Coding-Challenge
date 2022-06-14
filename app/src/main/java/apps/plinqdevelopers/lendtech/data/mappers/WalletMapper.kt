package apps.plinqdevelopers.lendtech.data.mappers

import apps.plinqdevelopers.lendtech.data.domain.DomainWallet
import apps.plinqdevelopers.lendtech.data.network.ApiWallet
import apps.plinqdevelopers.lendtech.data.utils.DataMapper
import javax.inject.Inject

class WalletMapper @Inject constructor() : DataMapper<DomainWallet, ApiWallet> {
    override fun mapToDomain(data: ApiWallet): DomainWallet {
        return DomainWallet(
            walletID = data.walletID,
            accountID = data.accountID,
            balance = data.balance
        )
    }

    override fun mapToApi(data: DomainWallet): ApiWallet {
        return ApiWallet(
            walletID = data.walletID,
            accountID = data.accountID,
            balance = data.balance
        )
    }
}