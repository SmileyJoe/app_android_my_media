package io.smileyjoe.media.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaItem(
    @SerialName("title")
    val title: String
)
