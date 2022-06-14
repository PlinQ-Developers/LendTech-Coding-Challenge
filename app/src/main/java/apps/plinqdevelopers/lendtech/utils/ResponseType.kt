package apps.plinqdevelopers.lendtech.utils

sealed class ResponseType<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : ResponseType<T>(data = data)
    class Loading<T>(data: T? = null) : ResponseType<T>(data = data)
    class Error<T>(data: T? = null, throwable: Throwable) :
        ResponseType<T>(data = data, error = throwable)
}
