package io.smileyjoe.media.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Group(
    @SerialName("name")
    val name: String,
    @SerialName("items")
    val items: MutableList<MediaItem> = mutableListOf()
)
