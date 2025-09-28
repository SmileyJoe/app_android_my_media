package io.smileyjoe.media.ui.component.fab

import io.smileyjoe.media.ui.base.ViewModel
import kotlinx.coroutines.flow.update

class FabAddMediaViewModel : ViewModel<FabAddMediaUIState>(FabAddMediaUIState()) {

    fun show() =
        updateShowing(true)

    fun hide() =
        updateShowing(false)

    fun expand() =
        updateExpanded(true)

    fun contract() =
        updateExpanded(false)

    fun toggleExpanded() {
        if (uiState.value.isExpanded) contract() else expand()
    }

    private fun updateExpanded(expand: Boolean) {
        _uiState.update {
            it.copy(isExpanded = expand)
        }
    }

    private fun updateShowing(show: Boolean) {
        _uiState.update {
            it.copy(isShowing = show)
        }
    }

}