package io.smileyjoe.media.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Web
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Web
import androidx.compose.ui.graphics.vector.ImageVector
import io.smileyjoe.media.R

enum class MediaItemType(
    @StringRes val title: Int,
    val iconSelected: ImageVector,
    val iconUnSelected: ImageVector,
    val showInTab: Boolean
) {
    APP(
        title = R.string.media_item_type_app,
        iconSelected = Icons.Filled.Apps,
        iconUnSelected = Icons.Outlined.Apps,
        showInTab = true
    ),
    URL(
        title = R.string.media_item_type_url,
        iconSelected = Icons.Filled.Web,
        iconUnSelected = Icons.Outlined.Web,
        showInTab = true
    ),
    NEW(
        title = R.string.text_new,
        iconSelected = Icons.Filled.Add,
        iconUnSelected = Icons.Outlined.Add,
        showInTab = false
    )
}