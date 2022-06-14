package apps.plinqdevelopers.lendtech.data.mappers

import apps.plinqdevelopers.lendtech.data.domain.DomainAccount
import apps.plinqdevelopers.lendtech.data.network.ApiAccount
import apps.plinqdevelopers.lendtech.data.utils.DataMapper
import javax.inject.Inject

class AccountMapper @Inject constructor()  : DataMapper<DomainAccount, ApiAccount> {
    override fun mapToDomain(data: ApiAccount): DomainAccount {
        return DomainAccount(
            authID = data.authID,
            authToken = data.authToken,
            authProfile = data.authProfile,
            authEmail = data.authEmail,
            authName = data.authName,
            authPassword = data.authPassword
        )
    }

    override fun mapToApi(data: DomainAccount): ApiAccount {
        TODO("Not yet implemented")
    }
}