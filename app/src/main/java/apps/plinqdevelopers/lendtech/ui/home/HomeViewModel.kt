package apps.plinqdevelopers.lendtech.ui.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import apps.plinqdevelopers.lendtech.data.domain.DomainPreferences
import apps.plinqdevelopers.lendtech.data.domain.DomainTransaction
import apps.plinqdevelopers.lendtech.data.domain.DomainWallet
import apps.plinqdevelopers.lendtech.data.payload.PayloadRequest
import apps.plinqdevelopers.lendtech.repositories.ServicesRepository
import apps.plinqdevelopers.lendtech.utils.Preferences
import apps.plinqdevelopers.lendtech.utils.ResponseType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val repository : ServicesRepository,
    private val preferences : Preferences
) : ViewModel(), LifecycleObserver {

    fun loadWallet(paramID : PayloadRequest, authToken : String) : LiveData<ResponseType<DomainWallet>> {
        return repository.loadWallet(paramID = paramID, authToken = authToken).asLiveData()
    }

    fun getLatestTransactions(paramID : PayloadRequest, authToken : String) : LiveData<ResponseType<List<DomainTransaction>>> {
        return repository.loadTransactions(paramID = paramID, authToken = authToken).asLiveData()
    }

    val appPreferences : Flow<DomainPreferences> = preferences.preferencesFlow
}