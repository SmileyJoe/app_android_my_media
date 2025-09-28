package io.smileyjoe.media.ui.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import io.smileyjoe.media.R
import io.smileyjoe.media.models.Group
import io.smileyjoe.media.ui.theme.Dimens

@Composable
fun DialogGroupAdd(
    onSave: (Group) -> Unit,
    onCancel: () -> Unit
) {
    val viewModel: DialogGroupAddViewModel = viewModel()
    var groupName by viewModel.groupName
    val padding = Dimens.padding

    Dialog(
        onDismissRequest = {
            viewModel.cancel()
            onCancel()
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
                        start = padding.medium,
                        end = padding.medium,
                        bottom = padding.small
                    )
            ) {
                Text(
                    text = stringResource(R.string.title_group_add),
                    style = MaterialTheme.typography.headlineSmall
                )
                TextField(
                    value = groupName,
                    onValueChange = { groupName = it },
                    label = {
                        Text(stringResource(R.string.hint_group_name))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = padding.medium)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = padding.medium)
                ) {
                    TextButton(
                        onClick = {
                            viewModel.cancel()
                            onCancel()
                        }
                    ) {
                        Text(text = stringResource(R.string.button_cancel))
                    }
                    TextButton(
                        onClick = {
                            onSave(viewModel.save())
                        }
                    ) {
                        Text(text = stringResource(R.string.button_save))
                    }
                }
            }
        }
    }
}