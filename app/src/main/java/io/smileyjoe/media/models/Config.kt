package io.smileyjoe.media.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Config(
    @SerialName("groups")
    val groups: MutableList<Group> = mutableListOf()
) {
    fun toJson() =
        Json.encodeToString(this)
}
