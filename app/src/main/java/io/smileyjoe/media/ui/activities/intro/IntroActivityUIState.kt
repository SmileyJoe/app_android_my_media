package io.smileyjoe.media.ui.activities.intro

import io.smileyjoe.media.ui.base.UIState

data class IntroActivityUIState(
    val showSplash: Boolean = true,
    val showConfirmDirectory: Boolean = false,
    val showChooseDirectory: Boolean = false,
    val showError: Boolean = false,
    val showLoading: Boolean = false,
    val isFileSaved: Boolean = false
) : UIState

fun IntroActivityUIState.hideError() = copy(
    showError = false
)

fun IntroActivityUIState.hideLoading() = copy(
    showLoading = false
)

fun IntroActivityUIState.showLoading() = copy(
    showLoading = true
)

fun IntroActivityUIState.hideConfirmDirectory() = copy(
    showConfirmDirectory = false
)

fun IntroActivityUIState.directoryUpdated(hasDirectory: Boolean) = copy(
    showChooseDirectory = true,
    showConfirmDirectory = hasDirectory,
    showLoading = false,
    showError = !hasDirectory
)

fun IntroActivityUIState.initial() = copy(
    showSplash = false,
    showLoading = false,
    showConfirmDirectory = false,
    showChooseDirectory = true,
    showError = false
)

fun IntroActivityUIState.directoryLoaded(showError: Boolean, isFileSaved: Boolean) = copy(
    showSplash = false,
    showLoading = false,
    showConfirmDirectory = false,
    showChooseDirectory = true,
    showError = showError,
    isFileSaved = isFileSaved
)
