package io.smileyjoe.media.ui.component.dialog.loading

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import io.smileyjoe.media.ui.theme.Dimens

@Preview
@Composable
fun DialogLoadingPreview() {
    DialogLoading(
        onDismiss = {}
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
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
        val iconSize = Dimens.icon
        Column(
            modifier = Modifier
                .padding(
                    top = padding.medium,
                    bottom = padding.medium
                )
        ) {
            LoadingIndicator(
                modifier = Modifier.size(iconSize.extraLarge)
            )
        }
    }
}