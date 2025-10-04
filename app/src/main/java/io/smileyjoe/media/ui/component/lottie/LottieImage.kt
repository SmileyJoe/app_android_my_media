package io.smileyjoe.media.ui.component.lottie

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties

@Composable
fun LottieImage(
    image: LottieFile,
    modifier: Modifier = Modifier,
    iterations: Int = LottieConstants.IterateForever
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(image.image))
    LottieAnimation(
        composition,
        iterations = iterations,
        dynamicProperties = rememberLottieDynamicProperties(*image.getProperties()),
        modifier = modifier.aspectRatio(image.aspectRatio),
        contentScale = ContentScale.Fit
    )
}