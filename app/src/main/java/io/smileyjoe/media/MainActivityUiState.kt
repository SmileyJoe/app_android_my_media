package io.smileyjoe.media

import io.smileyjoe.media.ui.base.UIState

data class MainActivityUiState(
    val isDialogAddGroupShowing: Boolean = false,
    val isFabAddExpanded: Boolean = false
) : UIState
