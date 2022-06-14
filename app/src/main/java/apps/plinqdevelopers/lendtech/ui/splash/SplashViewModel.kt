package apps.plinqdevelopers.lendtech.ui.splash

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import apps.plinqdevelopers.lendtech.data.domain.DomainPreferences
import apps.plinqdevelopers.lendtech.utils.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(
    private val preferences: Preferences
) : ViewModel(), LifecycleObserver {

    val appPreferences : Flow<DomainPreferences> = preferences.preferencesFlow

}