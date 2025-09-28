package io.smileyjoe.media.ui.component.fab

import io.smileyjoe.media.ui.base.UIState

data class FabAddMediaUIState(
    val isShowing: Boolean = true,
    val isExpanded: Boolean = false
) : UIState
