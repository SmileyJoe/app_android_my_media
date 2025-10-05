package io.smileyjoe.media.ui.activities.main

import android.app.Application
import android.net.Uri
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
                updateUi {
                    it.copy(isLoading = false)
                }
            } ?: run {
                errorMessage.value = R.string.error_file_not_found

                updateUi {
                    it.copy(isLoading = false, isErrorShowing = true)
                }
            }
        }

        updateUi {
            it.copy(isLoading = true)
        }
    }

    fun addGroup(group: Group) {
        updateUi {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            config.groups.add(group)
            val success = uri?.write(application, config.toJson()) ?: false

            if (success) {
                _groups.value = _groups.value + group
                updateUi {
                    it.copy(isLoading = false)
                }
            } else {
                updateUi {
                    errorMessage.value = R.string.error_file_not_found
                    it.copy(isErrorShowing = false)
                }
            }
        }
    }

    fun hideError() {
        updateUi {
            it.copy(
                isErrorShowing = false
            )
        }
        errorMessage.value = null
    }

    fun hideLoading() {
        updateUi {
            it.copy(
                isLoading = false
            )
        }
    }

    fun showDialogGroupAdd(show: Boolean) {
        updateUi {
            it.copy(isDialogAddGroupShowing = show)
        }
    }

    fun expandFabAddGroup(expand: Boolean) {
        updateUi {
            it.copy(isFabAddExpanded = expand)
        }
    }
}