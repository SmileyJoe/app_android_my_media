package io.smileyjoe.media.ui.component.dialog.loading

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import io.smileyjoe.media.lottie.LottieLoading
import io.smileyjoe.media.ui.component.lottie.LottieImage
import io.smileyjoe.media.ui.theme.Dimens

@Preview
@Composable
fun DialogLoadingPreview() {
    DialogLoading(
        onDismiss = {}
    )
}

@Composable
fun DialogLoading(
    onDismiss: () -> Unit
) {
    val padding = Dimens.padding

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Card(
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = padding.medium,
                        bottom = padding.medium
                    )
            ) {
                LottieImage(
                    image = LottieLoading(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}