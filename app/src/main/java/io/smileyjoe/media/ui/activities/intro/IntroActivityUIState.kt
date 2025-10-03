package io.smileyjoe.media.ui.activities.intro

import io.smileyjoe.media.ui.base.UIState

data class IntroActivityUIState(
    val showConfirmFile: Boolean = false,
    val showChooseFile: Boolean = false,
    val showError: Boolean = false,
    val showLoading: Boolean = false,
    val isFileSaved: Boolean = false
) : UIState
