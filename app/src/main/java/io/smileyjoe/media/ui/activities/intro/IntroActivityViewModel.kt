package io.smileyjoe.media.ui.activities.intro

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import io.smileyjoe.media.R
import io.smileyjoe.media.db.dataStore
import io.smileyjoe.media.models.Config
import io.smileyjoe.media.models.FileInfo
import io.smileyjoe.media.ui.base.AndroidViewModelUIState
import io.smileyjoe.media.utils.info
import io.smileyjoe.media.utils.write
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IntroActivityViewModel(application: Application) :
    AndroidViewModelUIState<IntroActivityUIState>(
        application,
        IntroActivityUIState()
    ) {

    var fileInfo: MutableState<FileInfo?> = mutableStateOf(null)
    var errorMessage: MutableState<Int?> = mutableStateOf(null)
    var selectedUri: Uri? = null

    init {
        viewModelScope.launch {
            application.dataStore.getConfigUri().collect {
                if (it != null) {
                    errorMessage.value = null
                    var success = true

                    if (fileInfo.value == null) {
                        // todo: Verify file contents
                        success = it.info(application) != null

                        if (!success) {
                            saveFile(null)
                            errorMessage.value = R.string.error_file_not_found_saved
                        }
                    }

                    _uiState.update {
                        it.copy(
                            showLoading = false,
                            showConfirmFile = false,
                            showError = errorMessage.value != null,
                            isFileSaved = success
                        )
                    }

                }
            }
        }

        _uiState.update {
            it.copy(showChooseFile = true)
        }
    }

    fun saveFile(uri: Uri? = selectedUri) {
        uri?.let {
            val takeFlags: Int =
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            application.contentResolver.takePersistableUriPermission(it, takeFlags)
        }

        viewModelScope.launch {
            application.dataStore.saveConfigUri(uri)
        }
    }

    fun fileLoaded(uri: Uri?) =
        handleFile(uri)

    fun fileCreated(uri: Uri?) =
        handleFile(uri, Config())

    private fun handleFile(uri: Uri?, config: Config? = null) {
        _uiState.update {
            it.copy(showLoading = true)
        }
        selectedUri = uri
        errorMessage.value = null

        viewModelScope.launch {
            errorMessage.value = uri?.info(application)?.let { fileInfo ->
                // todo: Verify file contents
                val success = config?.let {
                    uri.write(application, Config().toJson())
                } ?: true

                if (success) {
                    this@IntroActivityViewModel.fileInfo.value = fileInfo
                    null
                } else {
                    R.string.error_file_not_writeable
                }
            } ?: R.string.error_file_not_found

            val hasFile = this@IntroActivityViewModel.fileInfo.value != null
            _uiState.update {
                it.copy(
                    showChooseFile = true,
                    showConfirmFile = hasFile,
                    showLoading = false,
                    showError = !hasFile
                )
            }
        }
    }

    fun hideError() {
        _uiState.update {
            it.copy(
                showError = false
            )
        }
        errorMessage.value = null
    }

    fun hideConfirmFile() {
        _uiState.update {
            it.copy(
                showConfirmFile = false
            )
        }
    }

}