package io.smileyjoe.media.ui.activities.main

import android.app.Application
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import io.smileyjoe.media.R
import io.smileyjoe.media.db.ConfigDirectory
import io.smileyjoe.media.models.Config
import io.smileyjoe.media.models.Group
import io.smileyjoe.media.ui.base.AndroidViewModelUIState
import io.smileyjoe.media.utils.read
import io.smileyjoe.media.utils.write
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) :
    AndroidViewModelUIState<MainActivityUiState>(application, MainActivityUiState()) {

    private var config: Config = Config()

    private val _groups = MutableStateFlow<List<Group>>(listOf())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    var errorMessage: MutableState<Int?> = mutableStateOf(null)

    var configDir: ConfigDirectory? = null

    init {
        viewModelScope.launch {
            ConfigDirectory.get(application)?.let { dir ->
                configDir = dir
                configDir?.dataFile?.read(application)?.let {
                    config = Config.fromJson(it)
                    updateGroups()
                    showLoading(false)
                } ?: run {
                    showError(true, R.string.error_file_not_found)
                }
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
            val success = configDir?.dataFile?.write(application, config.toJson()) ?: false

            if (success) {
                updateGroups()
                showLoading(false)
            } else {
                config.groups.remove(group)
                showError(true, R.string.error_file_not_found)
            }
        }
    }

    fun updateGroups() {
        _groups.value = config.groups.toList()
            .sortedWith(
                comparator = compareBy(
                    comparator = String.CASE_INSENSITIVE_ORDER,
                    selector = { it.name }
                )
            )
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