package io.smileyjoe.media.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class AndroidViewModelUIState<T : UIState>(
    application: Application,
    uiState: T
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(uiState)
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    protected fun updateUi(ui: (T) -> T) {
        _uiState.update {
            ui(it)
        }
    }
}