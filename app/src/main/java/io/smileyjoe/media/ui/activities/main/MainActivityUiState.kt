package io.smileyjoe.media.ui.activities.main

import io.smileyjoe.media.ui.base.UIState

data class MainActivityUiState(
    val showDialogAddGroup: Boolean = false,
    val expandFabAdd: Boolean = false,
    val showError: Boolean = false,
    val showLoading: Boolean = false,
    val showDialogMediaItemAdd: Boolean = false
) : UIState
