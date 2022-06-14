package apps.plinqdevelopers.lendtech.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

class Constants {
    companion object {
        const val API_BASE_URL = "https://safirisolo-dev.wedo360.africa/app/"
        const val APP_PREFERENCES_NAME = "apps.plinqdevelopers.lendtech.datastore"

        val APP_PREFERENCES_USERNAME = stringPreferencesKey( "apps.plinqdevelopers.lendtech.USERNAME")
        val APP_PREFERENCES_EMAIL = stringPreferencesKey("apps.plinqdevelopers.lendtech.EMAIL")
        val APP_PREFERENCES_TOKEN = stringPreferencesKey("apps.plinqdevelopers.lendtech.TOKEN")
        val APP_PREFERENCES_ID = stringPreferencesKey("apps.plinqdevelopers.lendtech.ID")
        val APP_PREFERENCES_STATUS = booleanPreferencesKey("apps.plinqdevelopers.lendtech.STATUS")
    }
}