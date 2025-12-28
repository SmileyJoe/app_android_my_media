package io.smileyjoe.media.models

import android.content.Context
import io.smileyjoe.media.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Config(
    @SerialName("groups")
    val groups: MutableList<Group> = mutableListOf()
) {

    companion object {
        fun getDefault(context: Context) = Config(
            groups = mutableListOf(
                Group(
                    name = context.getString(R.string.group_name_services)
                )
            )
        )

        fun fromJson(json: String) =
            Json.decodeFromString<Config>(json)
    }

    fun toJson() =
        Json.encodeToString(this)
}
