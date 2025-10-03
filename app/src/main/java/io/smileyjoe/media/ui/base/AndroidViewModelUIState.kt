package io.smileyjoe.media.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class AndroidViewModelUIState<T : UIState>(
    application: Application,
    uiState: T
) : AndroidViewModel(application) {

    protected val _uiState = MutableStateFlow(uiState)
    val uiState: StateFlow<T> = _uiState.asStateFlow()

}