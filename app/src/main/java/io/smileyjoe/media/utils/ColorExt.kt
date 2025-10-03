package io.smileyjoe.media.utils

import androidx.annotation.ColorInt
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

@ColorInt
fun Color.saturation(percent: Float): Int =
    edit(
        position = 1,
        percent = percent
    )

private fun Color.edit(position: Int, percent: Float) =
    ColorUtils.HSLToColor(with(FloatArray(3)) {
        ColorUtils.colorToHSL(toArgb(), this)
        set(position, get(position) * (percent / 100))
        this
    })