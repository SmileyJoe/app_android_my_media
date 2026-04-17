package io.smileyjoe.media.utils

import android.net.Uri
import androidx.core.net.toUri

fun String.toUrl(): Uri =
    if (!startsWith("http://") && !startsWith("https://")) {
        "http://${this}"
    } else {
        this
    }.toUri()