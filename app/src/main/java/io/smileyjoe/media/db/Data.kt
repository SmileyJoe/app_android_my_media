package io.smileyjoe.media.db

import android.content.Context
import io.smileyjoe.media.models.Config

object Data {

//    fun load(context: Context) : Config {
//
//    }

    suspend fun save(context: Context, config: Config) {
        context.dataStore.getConfigUri().collect {
            it?.let {

            }
        }
    }

}