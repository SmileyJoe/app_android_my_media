package io.smileyjoe.media.lottie

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import io.smileyjoe.media.R
import io.smileyjoe.media.ui.component.lottie.LottieFile
import io.smileyjoe.media.ui.component.lottie.LottieStyle

class LottieSaving(color: Color) : LottieFile(
    image = R.raw.saving,
    aspectRatio = 900 / 900f,
    styles = listOf(
        LottieStyle(
            color = color.toArgb(),
            paths = listOf(
                layer("Layer 1", "Group 4")
            )
        )
    )
)