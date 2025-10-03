package io.smileyjoe.media.ui.component.dialog.confirm

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.smileyjoe.media.R
import io.smileyjoe.media.ui.component.dialog.error.DialogError

@Composable
fun DialogConfirm(
    @StringRes messageResId: Int? = null,
    @StringRes titleResId: Int = R.string.dialog_title_confirm,
    message: String? = null,
    onDismiss: () -> Unit,
    onPositive: () -> Unit,
    onNegative: () -> Unit = onDismiss
) {
    if (messageResId == null && message == null) {
        DialogError(
            onDismiss = onNegative
        )
    } else {
        AlertDialog(
            title = {
                Text(text = stringResource(titleResId))
            },
            onDismissRequest = {
                onDismiss()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onPositive()
                    }
                ) {
                    Text(stringResource(R.string.button_yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onNegative()
                    }
                ) {
                    Text(stringResource(R.string.button_no))
                }
            },
            text = {
                Text(message ?: stringResource(messageResId!!))
            }
        )
    }
}