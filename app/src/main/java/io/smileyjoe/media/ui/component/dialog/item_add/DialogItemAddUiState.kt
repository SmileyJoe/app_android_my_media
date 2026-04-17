package io.smileyjoe.media.ui.component.dialog.item_add

import io.smileyjoe.media.ui.base.UIState

data class DialogItemAddUiState(
    val isAppShowing: Boolean = false,
    val isUrlShowing: Boolean = false
) : UIState
