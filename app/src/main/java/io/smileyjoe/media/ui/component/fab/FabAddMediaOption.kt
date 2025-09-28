package io.smileyjoe.media.ui.component.fab

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.ui.graphics.vector.ImageVector
import io.smileyjoe.media.R

enum class FabAddMediaOption(
    @StringRes val titleRes: Int,
    val icon: ImageVector
) {
    GROUP(
        titleRes = R.string.fab_add_group,
        icon = Icons.Filled.GroupAdd
    )
}