package io.smileyjoe.media.models

data class Group(
    val name: String,
    val items: MutableList<MediaItem> = mutableListOf()
)
