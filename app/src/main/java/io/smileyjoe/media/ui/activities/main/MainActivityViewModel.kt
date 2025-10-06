package io.smileyjoe.media.ui.activities.main

import android.app.Application
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import io.smileyjoe.media.R
import io.smileyjoe.media.db.dataStore
import io.smileyjoe.media.models.Config
import io.smileyjoe.media.models.Group
import io.smileyjoe.media.ui.base.AndroidViewModelUIState
import io.smileyjoe.media.utils.read
import io.smileyjoe.media.utils.write
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) :
    AndroidViewModelUIState<MainActivityUiState>(application, MainActivityUiState()) {

    private var config: Config = Config()

    private val _groups = MutableStateFlow<List<Group>>(listOf())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    var errorMessage: MutableState<Int?> = mutableStateOf(null)

    var uri: Uri? = null

    init {
        viewModelScope.launch {
            uri = application.dataStore.getConfigUri().first()
            uri?.read(application)?.let {
                config = Config.fromJson(it)
                _groups.value = config.groups
                showLoading(false)
            } ?: run {
                showError(true, R.string.error_file_not_found)
            }
        }

        showLoading(true)
    }

    fun addGroup(group: Group) {
        showLoading(true)

        viewModelScope.launch {
            config.groups.add(group)
            val success = uri?.write(application, config.toJson()) ?: false

            if (success) {
                _groups.value = _groups.value + group
                showLoading(false)
            } else {
                showError(true, R.string.error_file_not_found)
            }
        }
    }

    fun showError(show: Boolean, @StringRes message: Int? = null) {
        updateUi {
            it.copy(
                showError = show,
                showLoading = false
            )
        }

        errorMessage.value = message
    }

    fun showLoading(show: Boolean) {
        updateUi {
            it.copy(showLoading = show)
        }
    }

    fun showDialogGroupAdd(show: Boolean) {
        updateUi {
            it.copy(showDialogAddGroup = show)
        }
    }

    fun expandFabAddGroup(expand: Boolean) {
        updateUi {
            it.copy(expandFabAdd = expand)
        }
    }
}