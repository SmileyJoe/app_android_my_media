package io.smileyjoe.media.lottie

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import io.smileyjoe.media.R
import io.smileyjoe.media.ui.component.lottie.LottieFile
import io.smileyjoe.media.ui.component.lottie.LottieStyle
import io.smileyjoe.media.utils.lightness

class LottieLoading(color: Color) : LottieFile(
    image = R.raw.loading,
    aspectRatio = 800/600f,
    styles = listOf(
        LottieStyle(
            color = color.lightness(30f),
            paths = listOf(
                layer("**", "planete Outlines - Group 1", "Group 1")
            )
        ),
        LottieStyle(
            color = color.lightness(50f),
            paths = listOf(
                layer("**", "planete Outlines - Group 2", "Group 2")
            )
        ),
        LottieStyle(
            color = color.toArgb(),
            paths = listOf(
                layer("**", "planete Outlines - Group 3", "Group 3")
            )
        )
    )
)