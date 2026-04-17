package io.smileyjoe.media.ui.activities.main

import android.content.Intent

sealed class MainActivityEvent {
    data class openUrl(val intent: Intent) : MainActivityEvent()
    data class openApplication(val intent: Intent) : MainActivityEvent()
}