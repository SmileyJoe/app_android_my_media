package io.smileyjoe.media.ui.component.dialog.error

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.smileyjoe.media.R
import io.smileyjoe.media.ui.theme.Dimens

@Preview
@Composable
fun DialogErrorPreview() {
    DialogError(
        messageResId = R.string.error_generic,
        onDismiss = {}
    )
}

@Composable
fun DialogError(
    @StringRes messageResId: Int? = null,
    @StringRes titleResId: Int = R.string.dialog_title_error,
    message: String? = null,
    onDismiss: () -> Unit
) {
    val iconSize = Dimens.icon
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Error",
                modifier = Modifier.size(iconSize.extraLarge)
            )
        },
        title = {
            Text(text = stringResource(titleResId))
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.button_ok))
            }
        },
        text = {
            Text(
                text = message ?: stringResource(messageResId ?: R.string.error_generic),
                textAlign = TextAlign.Center
            )
        }
    )
}