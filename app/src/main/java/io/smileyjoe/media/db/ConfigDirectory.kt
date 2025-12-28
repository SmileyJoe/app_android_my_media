package io.smileyjoe.media.db

import android.content.Context
import androidx.documentfile.provider.DocumentFile
import io.smileyjoe.media.utils.asDirectory
import io.smileyjoe.media.utils.info
import kotlinx.coroutines.flow.first

class ConfigDirectory(dir: DocumentFile) {

    companion object {
        const val FILENAME_DATA = "data.json"
        const val MIME_DATA = "application/json"
        suspend fun get(context: Context): ConfigDirectory? =
            context.dataStore.getConfigUri().first()?.asDirectory(context)?.let {
                return@let ConfigDirectory(it)
            }
    }

    val info = dir.info()
    val dataFile = dir.findFile("data.json")?.uri
    val isValid: Boolean =
        info != null && dataFile != null

}