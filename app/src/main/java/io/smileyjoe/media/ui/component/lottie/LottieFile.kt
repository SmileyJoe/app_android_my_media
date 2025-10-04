package io.smileyjoe.media.ui.component.lottie

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieDynamicProperty
import com.airbnb.lottie.compose.rememberLottieDynamicProperty

open class LottieFile(
    @RawRes val image: Int,
    val aspectRatio: Float,
    val styles: List<LottieStyle>
) {

    companion object {
        fun layer(vararg names: String): Array<String> =
            arrayOf(*names, "**")
    }

    @Composable
    fun getProperties(): Array<LottieDynamicProperty<Int>> {
        val properties = mutableListOf<LottieDynamicProperty<Int>>()
        styles.map { style ->
            style.paths.map { path ->
                properties.add(
                    rememberLottieDynamicProperty(
                        property = LottieProperty.COLOR,
                        value = style.color,
                        keyPath = path
                    )
                )
            }
        }
        return properties.toTypedArray()
    }

}
