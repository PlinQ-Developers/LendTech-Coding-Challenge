package apps.plinqdevelopers.lendtech.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import apps.plinqdevelopers.lendtech.data.domain.DomainPreferences
import apps.plinqdevelopers.lendtech.utils.Constants.Companion.APP_PREFERENCES_EMAIL
import apps.plinqdevelopers.lendtech.utils.Constants.Companion.APP_PREFERENCES_ID
import apps.plinqdevelopers.lendtech.utils.Constants.Companion.APP_PREFERENCES_NAME
import apps.plinqdevelopers.lendtech.utils.Constants.Companion.APP_PREFERENCES_STATUS
import apps.plinqdevelopers.lendtech.utils.Constants.Companion.APP_PREFERENCES_TOKEN
import apps.plinqdevelopers.lendtech.utils.Constants.Companion.APP_PREFERENCES_USERNAME
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class Preferences
@Inject
constructor(
    private val dataStore : DataStore<Preferences>
){

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_PREFERENCES_NAME)

    val appAuthenticationStatus = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[APP_PREFERENCES_STATUS]
        }


    suspend fun updateAuthenticationStatus(status: Boolean) {
        dataStore.edit { preference ->
            preference[APP_PREFERENCES_STATUS] = status
        }
    }

    val preferencesFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            mapPreferences(preferences = preferences)
        }

    suspend fun updateAuthPreferences(preferences: DomainPreferences) {
        dataStore.edit { prefs ->
            prefs[APP_PREFERENCES_ID] = preferences.authID
            prefs[APP_PREFERENCES_USERNAME] = preferences.authName
            prefs[APP_PREFERENCES_EMAIL] = preferences.authEmail
            prefs[APP_PREFERENCES_TOKEN] = preferences.authToken
            prefs[APP_PREFERENCES_STATUS] = preferences.authStatus
        }
    }

    private fun mapPreferences(preferences: Preferences) : DomainPreferences {
        val authID = preferences[APP_PREFERENCES_ID] ?: ""
        val authName = preferences[APP_PREFERENCES_USERNAME] ?: ""
        val authEmail = preferences[APP_PREFERENCES_EMAIL] ?: ""
        val authToken = preferences[APP_PREFERENCES_TOKEN] ?: ""
        val authStatus = preferences[APP_PREFERENCES_STATUS] ?: false

        return DomainPreferences(
            authID,
            authName,
            authToken,
            authEmail,
            authStatus
        )
    }

}