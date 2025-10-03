package io.smileyjoe.media.db

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.preferences: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)
val Context.dataStore
    get() = DataStore(this)

class DataStore(context: Context) {

    val preferences = context.preferences
    val keyConfigUri = stringPreferencesKey("config_uri")

    fun getConfigUri(): Flow<Uri?> =
        preferences.data.catch {
            emit(emptyPreferences())
        }.map { prefs ->
            prefs[keyConfigUri]?.toUri()
        }

    suspend fun saveConfigUri(uri: Uri?) {
        preferences.edit { prefs ->
            uri?.let {
                prefs[keyConfigUri] = it.toString()
            } ?: run {
                prefs.remove(keyConfigUri)
            }
        }
    }
}