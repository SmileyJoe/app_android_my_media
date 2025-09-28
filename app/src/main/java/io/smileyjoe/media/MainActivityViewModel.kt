package io.smileyjoe.media

import io.smileyjoe.media.models.Group
import io.smileyjoe.media.ui.base.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivityViewModel : ViewModel<MainActivityUiState>(MainActivityUiState()) {

    private val _groups = MutableStateFlow<List<Group>>(listOf())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    fun addGroup(group: Group) {
        _groups.value = _groups.value + group
    }
}