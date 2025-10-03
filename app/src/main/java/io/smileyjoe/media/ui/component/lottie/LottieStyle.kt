package io.smileyjoe.media.ui.component.lottie

import androidx.annotation.ColorInt

data class LottieStyle(
    @ColorInt val color: Int,
    val paths: List<Array<String>>
)
