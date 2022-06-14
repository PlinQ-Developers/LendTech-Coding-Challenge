package apps.plinqdevelopers.lendtech.utils

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchedResults: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {

    val listData = query().first()

    val flowResult = if (shouldFetch(listData)) {
        emit(ResponseType.Loading(listData))
        try {
            saveFetchedResults(fetch())
            query().map { ResponseType.Success(data = it) }
        } catch (throwable: Throwable) {
            query().map { ResponseType.Error(data = it, throwable = throwable) }
        }
    } else {
        query().map { ResponseType.Success(data = it) }
    }

    emitAll(flowResult)
}