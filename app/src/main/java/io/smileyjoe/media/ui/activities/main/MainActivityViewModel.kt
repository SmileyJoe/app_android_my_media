package io.smileyjoe.media.ui.activities.main

import android.app.Application
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import io.smileyjoe.media.R
import io.smileyjoe.media.db.ConfigDirectory
import io.smileyjoe.media.models.Config
import io.smileyjoe.media.models.Group
import io.smileyjoe.media.models.MediaItem
import io.smileyjoe.media.models.MediaItemType
import io.smileyjoe.media.ui.base.AndroidViewModelUIState
import io.smileyjoe.media.utils.getBrowserIntent
import io.smileyjoe.media.utils.read
import io.smileyjoe.media.utils.sortCaseInsensitive
import io.smileyjoe.media.utils.toUrl
import io.smileyjoe.media.utils.write
import io.smileyjoe.packages.getIntent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) :
    AndroidViewModelUIState<MainActivityUiState>(application, MainActivityUiState()) {

    private var config: Config = Config()

    private val _groups = MutableStateFlow<List<Group>>(listOf())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    private val _event = MutableSharedFlow<MainActivityEvent>()
    val event: SharedFlow<MainActivityEvent> = _event.asSharedFlow()

    var errorMessage: MutableState<Int?> = mutableStateOf(null)

    var configDir: ConfigDirectory? = null

    var selectedGroup: Group? = null

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

    fun addMediaItem(group: Group?, item: MediaItem) {
        showLoading(true)
        viewModelScope.launch {
            with(config.groups) {
                firstOrNull { it.name == group?.name }?.let { group ->
                    remove(group)
                    add(group.addItem(item))

                    val success = saveConfig()

                    if (success) {
                        showDialogMediaItemAdd(false)
                    } else {
                        group.items.remove(item)
                    }
                } ?: run {
                    showError(true, R.string.error_generic)
                }
            }
        }
    }

    private fun Group.addItem(item: MediaItem): Group =
        copy(
            items = items
                // Add the new item
                .apply { add(item) }
                // Deep copy so the ui redraws
                .map { it.copy() }
                // Sort the list
                .sortCaseInsensitive { it.title }
                // Put it back to a mutable list
                .toMutableList())

    fun addGroup(group: Group) {
        showLoading(true)

        viewModelScope.launch {
            config.groups.add(group)
            val success = saveConfig()

            if (!success) {
                config.groups.remove(group)
            }
        }
    }

    fun openItem(item: MediaItem) {
        when (item.type) {
            MediaItemType.URL -> {
                item.details
                    .toUrl()
                    .getBrowserIntent(application.packageManager)
                    ?.let {
                        launch(MainActivityEvent.openUrl(it))
                    } ?: run {
                    showError(true, R.string.error_generic)
                }
            }

            MediaItemType.APP -> {
                application.packageManager
                    .getIntent(item.details)
                    ?.let {
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        launch(MainActivityEvent.openApplication(it))
                    } ?: run {
                    showError(true, R.string.error_generic)
                }
            }

            else -> {
                // do nothing
            }
        }
    }

    private suspend fun saveConfig(): Boolean {
        val success = configDir?.dataFile?.write(application, config.toJson()) ?: false

        if (success) {
            updateGroups()
            showLoading(false)
        } else {
            showError(true, R.string.error_file_not_found)
        }

        return success
    }

    fun addMediaItemClicked(group: Group) {
        selectedGroup = group
        showDialogMediaItemAdd(true)
    }

    fun updateGroups() {
        _groups.value = config.groups.toList()
            .sortCaseInsensitive { it.name }
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

    fun showDialogMediaItemAdd(show: Boolean) {
        updateUi {
            it.copy(showDialogMediaItemAdd = show)
        }

        if (false) {
            selectedGroup = null
        }
    }

    fun launch(event: MainActivityEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}