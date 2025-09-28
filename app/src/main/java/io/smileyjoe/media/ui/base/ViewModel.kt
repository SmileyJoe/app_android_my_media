package io.smileyjoe.media.ui.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class ViewModel<T : UIState>(
    uiState: T
) : androidx.lifecycle.ViewModel() {

    protected val _uiState = MutableStateFlow(uiState)
    val uiState: StateFlow<T> = _uiState.asStateFlow()

}