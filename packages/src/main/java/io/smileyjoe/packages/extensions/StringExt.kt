package io.smileyjoe.packages.extensions

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * Generate a color based on the String.
 */
@ColorInt
fun String.toColor(): Int {
    // this is basically ripped from the web somewhere, but I don't have the links //
    val hash = hashCode()

    val red = (hash and 0xFF0000) shr 16
    val green = (hash and 0x00FF00) shr 8
    val blue = (hash and 0x0000FF)

    return Color.argb(255, red, green, blue)
}