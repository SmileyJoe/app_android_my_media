package io.smileyjoe.media.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import io.smileyjoe.media.R

data class Padding(
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp
)

object Dimens {
    val padding: Padding
        @Composable
        get() = Padding(
            extraSmall = dimensionResource(R.dimen.padding_x_small),
            small = dimensionResource(R.dimen.padding_small),
            medium = dimensionResource(R.dimen.padding_medium),
            large = dimensionResource(R.dimen.padding_large),
            extraLarge = dimensionResource(R.dimen.padding_x_large)
        )
}