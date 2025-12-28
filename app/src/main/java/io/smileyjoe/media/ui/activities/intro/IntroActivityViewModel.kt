package io.smileyjoe.media.ui.activities.intro

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import io.smileyjoe.media.R
import io.smileyjoe.media.db.ConfigDirectory
import io.smileyjoe.media.db.dataStore
import io.smileyjoe.media.exception.FileNotWriteableException
import io.smileyjoe.media.models.Config
import io.smileyjoe.media.models.FileInfo
import io.smileyjoe.media.ui.base.AndroidViewModelUIState
import io.smileyjoe.media.utils.asDirectory
import io.smileyjoe.media.utils.createIfNotExists
import io.smileyjoe.media.utils.info
import io.smileyjoe.media.utils.write
import kotlinx.coroutines.launch
import okio.FileNotFoundException

class IntroActivityViewModel(
    application: Application
) : AndroidViewModelUIState<IntroActivityUIState>(
    application = application,
    uiState = IntroActivityUIState()
) {

    var fileInfo: MutableState<FileInfo?> = mutableStateOf(null)
    var errorMessage: MutableState<Int?> = mutableStateOf(null)
    var selectedUri: Uri? = null

    init {
        viewModelScope.launch {
            ConfigDirectory.get(application)?.let { dir ->
                if (!dir.isValid) {
                    saveDirectory(null)
                    errorMessage.value = R.string.error_file_not_found_saved
                }

                updateUi { ui ->
                    ui.directoryLoaded(
                        showError = errorMessage.value != null,
                        isFileSaved = dir.isValid
                    )
                }
            } ?: run {
                updateUi { ui ->
                    ui.initial()
                }
            }
        }
    }

    fun directorySelected(uri: Uri?) {
        updateUi {
            it.showLoading()
        }

        selectedUri = uri
        errorMessage.value = null

        viewModelScope.launch {
            uri?.asDirectory(application)?.info()?.let {
                fileInfo.value = it
            }

            updateUi {
                it.directoryUpdated(
                    hasDirectory = this@IntroActivityViewModel.fileInfo.value != null
                )
            }
        }
    }

    fun directoryConfirmed() {
        updateUi {
            it.showLoading()
        }

        viewModelScope.launch {
            val created = createData()

            if (created) {
                saveDirectory()
            }

            val configDir = ConfigDirectory.get(application)
            updateUi { ui ->
                ui.directoryLoaded(
                    showError = errorMessage.value != null,
                    isFileSaved = configDir?.isValid == true
                )
            }
        }
    }

    private suspend fun saveDirectory(uri: Uri? = selectedUri) {
        uri?.let {
            val takeFlags: Int =
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            application.contentResolver.takePersistableUriPermission(it, takeFlags)
        }

        application.dataStore.saveConfigUri(uri)
    }

    private fun createData(): Boolean =
        selectedUri?.asDirectory(application)?.let { dir ->
            try {
                dir.createIfNotExists(
                    mimeType = ConfigDirectory.MIME_DATA,
                    fileName = ConfigDirectory.FILENAME_DATA,
                    created = { file ->
                        viewModelScope.launch {
                            file.write(application, Config.getDefault(application).toJson())
                        }
                    }
                )
                true
            } catch (e1: FileNotFoundException) {
                errorMessage.value = R.string.error_file_not_found
                false
            } catch (e2: FileNotWriteableException) {
                errorMessage.value = R.string.error_file_not_writeable
                false
            }
        } ?: run {
            errorMessage.value = R.string.error_file_not_found
            false
        }

    fun hideError() {
        updateUi {
            it.hideError()
        }
        errorMessage.value = null
    }

    fun hideLoading() {
        updateUi {
            it.hideLoading()
        }
    }

    fun hideConfirmDirectory() {
        updateUi {
            it.hideConfirmDirectory()
        }
    }

}