package io.smileyjoe.media.models

import android.content.pm.PackageManager
import io.smileyjoe.packages.getIcon
import io.smileyjoe.packages.isInstalled
import io.smileyjoe.packages.objects.PackageInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaItem(
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: MediaItemType,
    @SerialName("detail")
    val details: String
)

fun MediaItem.toPackageInfo(packageManager: PackageManager?): PackageInfo? =
    packageManager?.let {
        when (type) {
            MediaItemType.APP -> PackageInfo(
                id = details,
                name = title,
                isInstalled = it.isInstalled(details),
                icon = it.getIcon(details)
            )

            else -> null
        }
    }

