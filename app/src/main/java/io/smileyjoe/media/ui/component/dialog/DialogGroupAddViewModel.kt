package io.smileyjoe.media.ui.component.dialog

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.smileyjoe.media.models.Group

class DialogGroupAddViewModel : ViewModel() {

    var groupName = mutableStateOf("")

    fun save(): Group {
        val group = Group(name = groupName.value)
        groupName.value = ""
        return group
    }

    fun cancel() {
        groupName.value = ""
    }
}