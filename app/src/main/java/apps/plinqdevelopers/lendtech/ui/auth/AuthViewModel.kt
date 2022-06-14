package apps.plinqdevelopers.lendtech.ui.auth

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import apps.plinqdevelopers.lendtech.data.domain.DomainAccount
import apps.plinqdevelopers.lendtech.data.domain.DomainPreferences
import apps.plinqdevelopers.lendtech.data.network.ApiValidation
import apps.plinqdevelopers.lendtech.data.payload.PayloadAccount
import apps.plinqdevelopers.lendtech.repositories.AuthRepository
import apps.plinqdevelopers.lendtech.utils.Preferences
import apps.plinqdevelopers.lendtech.utils.ResponseType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val repository : AuthRepository,
    private val preferences : Preferences
) : ViewModel(), LifecycleObserver {

    fun isEmailRegistered(payload : PayloadAccount) : LiveData<ResponseType<ApiValidation>> {
        return repository.isEmailRegistered(payload = payload).asLiveData()
    }

    fun accountLogin(payload: PayloadAccount): LiveData<ResponseType<DomainAccount>> {
        return repository.accountLogin(payload = payload).asLiveData()
    }

    suspend fun saveNewPreferences(prefs: DomainPreferences) {
        preferences.updateAuthPreferences(preferences = prefs)
    }

    suspend fun updateAuthenticationStatus(status : Boolean) {
        preferences.updateAuthenticationStatus(status = status)
    }
}