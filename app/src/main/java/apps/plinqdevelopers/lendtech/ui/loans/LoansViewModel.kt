package apps.plinqdevelopers.lendtech.ui.loans

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import apps.plinqdevelopers.lendtech.data.domain.DomainPreferences
import apps.plinqdevelopers.lendtech.data.domain.DomainTransaction
import apps.plinqdevelopers.lendtech.repositories.ServicesRepository
import apps.plinqdevelopers.lendtech.utils.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LoansViewModel
@Inject
constructor(
    private val repository : ServicesRepository,
    private val preferences : Preferences
) : ViewModel(), LifecycleObserver {

    fun getPaymentTransactions(filter : String) : LiveData<List<DomainTransaction>> {
        return repository.getFilteredTransactions(filterType = filter).asLiveData()
    }

    val appPreferences : Flow<DomainPreferences> = preferences.preferencesFlow

}