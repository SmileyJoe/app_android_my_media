package io.smileyjoe.media.ui.component.dialog

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.smileyjoe.media.R
import io.smileyjoe.media.ui.theme.Dimens

@Composable
fun ColumnScope.DialogButtonsDone(
    modifier: Modifier? = null,
    onCancel: (() -> Unit)? = null,
    onSave: (() -> Unit)? = null
) {
    val padding = Dimens.padding
    val rowModifier = modifier ?: Modifier
        .padding(top = padding.medium)
    Row(
        modifier = rowModifier
            .align(Alignment.End)
    ) {
        onCancel?.let {
            TextButton(
                onClick = {
                    it()
                }
            ) {
                Text(text = stringResource(R.string.button_cancel))
            }
        }
        onSave?.let {
            TextButton(
                onClick = {
                    it()
                }
            ) {
                Text(text = stringResource(R.string.button_save))
            }
        }
    }
}