package io.smileyjoe.media.ui.component.dialog

import androidx.compose.runtime.mutableStateOf
import io.smileyjoe.media.models.Group
import io.smileyjoe.media.ui.base.ViewModel
import kotlinx.coroutines.flow.update

class DialogGroupAddViewModel : ViewModel<DialogGroupAddUIState>(DialogGroupAddUIState()) {

    var groupName = mutableStateOf("")

    fun show() =
        updateShowing(true)

    fun hide() =
        updateShowing(false)

    fun save(): Group {
        val group = Group(name = groupName.value)
        groupName.value = ""
        hide()
        return group
    }

    fun cancel() {
        groupName.value = ""
        hide()
    }

    private fun updateShowing(show: Boolean) {
        _uiState.update {
            it.copy(isShowing = show)
        }
    }
}