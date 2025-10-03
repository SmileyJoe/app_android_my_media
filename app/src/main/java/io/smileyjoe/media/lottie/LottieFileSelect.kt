package io.smileyjoe.media.lottie

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import io.smileyjoe.media.R
import io.smileyjoe.media.ui.component.lottie.LottieFile
import io.smileyjoe.media.ui.component.lottie.LottieStyle
import io.smileyjoe.media.utils.saturation

class LottieFileSelect(val color: Color, val colorBackground: Color) : LottieFile(
    image = R.raw.file_select,
    styles = listOf(
        LottieStyle(
            color = colorBackground.toArgb(),
            paths = listOf(
                layer("Bg", "Group 1")
            )
        ),
        LottieStyle(
            color = color.saturation(50f),
            paths = listOf(
                layer("Frile Back", "Group 1")
            )
        ),
        LottieStyle(
            color = color.toArgb(),
            paths = listOf(
                layer("Folder Front", "Group 1"),
                layer("Folder Back", "Group 1"),
                layer("File Front", "Group 1"),
                layer("File Front", "Group 2"),
                layer("File Front", "Group 2", "Group 2"),
                layer("File Front", "Group 3"),
                layer("Sparkle", "Group 1"),
                layer("Sparkle 1", "Group 1"),
                layer("Sparkle 2", "Group 1")
            )
        )
    )
)
