package io.smileyjoe.media.ui.component.dialog.item_add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.smileyjoe.media.R
import io.smileyjoe.media.ui.theme.Dimens

@Composable
fun DialogItemAddTypeUrl(
    viewModel: DialogItemAddViewModel
) {
    InputTitle(
        viewModel = viewModel
    )
    InputUrl(
        viewModel = viewModel
    )
}

@Composable
private fun InputTitle(
    viewModel: DialogItemAddViewModel
) {
    var title by viewModel.title
    val padding = Dimens.padding
    TextField(
        value = title,
        onValueChange = { title = it },
        label = {
            Text(stringResource(R.string.hint_media_item_title))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding.medium)
    )
}

@Composable
private fun InputUrl(
    viewModel: DialogItemAddViewModel
) {
    var url by viewModel.url
    val padding = Dimens.padding
    TextField(
        value = url,
        onValueChange = { url = it },
        label = {
            Text(stringResource(R.string.hint_url))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding.medium)
    )
}