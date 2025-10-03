package io.smileyjoe.media.ui.activities.main

import io.smileyjoe.media.models.Group
import io.smileyjoe.media.ui.base.ViewModelUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel : ViewModelUIState<MainActivityUiState>(MainActivityUiState()) {

    private val _groups = MutableStateFlow<List<Group>>(listOf())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    fun addGroup(group: Group) {
        _groups.value = _groups.value + group
    }

    fun showDialogGroupAdd(show: Boolean) {
        _uiState.update {
            it.copy(isDialogAddGroupShowing = show)
        }
    }

    fun expandFabAddGroup(expand: Boolean) {
        _uiState.update {
            it.copy(isFabAddExpanded = expand)
        }
    }
}