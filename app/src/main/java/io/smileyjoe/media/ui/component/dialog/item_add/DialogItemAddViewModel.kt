package io.smileyjoe.media.ui.component.dialog.item_add

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.application
import io.smileyjoe.media.models.MediaItem
import io.smileyjoe.media.models.MediaItemType
import io.smileyjoe.media.ui.base.AndroidViewModelUIState
import io.smileyjoe.packages.getInstalled
import io.smileyjoe.packages.objects.PackageInfo

class DialogItemAddViewModel(
    application: Application
) : AndroidViewModelUIState<DialogItemAddUiState>(
    application,
    DialogItemAddUiState()
) {

    var url = mutableStateOf("")
    var title = mutableStateOf("")
    var selectedType: MediaItemType = MediaItemType.APP

    init {
        showApp()
    }

    fun showApp() {
        selectedType = MediaItemType.APP
        updateUi { it.copy(isAppShowing = true, isUrlShowing = false) }
    }

    fun showUrl() {
        selectedType = MediaItemType.URL
        updateUi { it.copy(isUrlShowing = true, isAppShowing = false) }
    }

    fun getPackages(): List<PackageInfo> =
        application.packageManager.getInstalled()

    fun saveUrl(): MediaItem {
        val mediaItem = MediaItem(
            title = title.value,
            type = MediaItemType.URL,
            details = url.value
        )
        url.value = ""
        title.value = ""
        return mediaItem
    }

    fun saveApp(details: PackageInfo): MediaItem {
        val mediaItem = MediaItem(
            title = details.name,
            type = MediaItemType.APP,
            details = details.id
        )
        url.value = ""
        title.value = ""
        return mediaItem
    }

    fun cancel() {
        url.value = ""
        title.value = ""
    }

}