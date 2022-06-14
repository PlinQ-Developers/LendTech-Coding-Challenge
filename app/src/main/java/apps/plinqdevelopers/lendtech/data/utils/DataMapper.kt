package apps.plinqdevelopers.lendtech.data.utils

interface DataMapper<Domain, Api> {
    fun mapToDomain(data : Api) : Domain
    fun mapToApi(data : Domain) : Api
}