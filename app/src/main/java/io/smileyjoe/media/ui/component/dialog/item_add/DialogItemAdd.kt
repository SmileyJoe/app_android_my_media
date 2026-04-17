package io.smileyjoe.media.ui.component.dialog.item_add

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import io.smileyjoe.media.R
import io.smileyjoe.media.models.MediaItem
import io.smileyjoe.media.models.MediaItemType
import io.smileyjoe.media.ui.component.dialog.DialogButtonsDone
import io.smileyjoe.media.ui.theme.Dimens

@Preview
@Composable
fun DialogItemAddPreview() {
    DialogItemAdd(
        onSave = {

        },
        onCancel = {

        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun DialogItemAdd(
    onSave: (MediaItem) -> Unit,
    onCancel: () -> Unit
) {
    val viewModel: DialogItemAddViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val padding = Dimens.padding
    Dialog(
        onDismissRequest = {
            onCancel()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = padding.medium,
                    vertical = padding.medium
                )
        ) {
            Card(
                Modifier
                    .fillMaxWidth()
            ) {
                when (LocalConfiguration.current.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> DialogItemAddLandscape(
                        onSave = onSave,
                        onCancel = onCancel,
                        viewModel = viewModel,
                        uiState = uiState
                    )

                    else -> DialogItemAddPortrait(
                        onSave = onSave,
                        onCancel = onCancel,
                        viewModel = viewModel,
                        uiState = uiState
                    )
                }
            }
        }
    }
}

@Composable
private fun Title() {
    Text(
        text = stringResource(R.string.title_item_add),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun ColumnScope.DialogItemAddPortrait(
    onSave: (MediaItem) -> Unit,
    onCancel: () -> Unit,
    viewModel: DialogItemAddViewModel,
    uiState: DialogItemAddUiState
) {
    val padding = Dimens.padding

    Column(
        modifier = Modifier.padding(
            start = padding.medium,
            end = padding.medium,
            top = padding.large,
            bottom = padding.small
        )
    ) {
        Title()
        Spacer(Modifier.size(padding.medium))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            MediaTypeSelector(
                onSelected = {
                    when (it) {
                        MediaItemType.APP -> viewModel.showApp()
                        MediaItemType.URL -> viewModel.showUrl()
                        else -> {
                            // do nothing
                        }
                    }
                },
                selectedType = viewModel.selectedType
            )
        }
        if (uiState.isUrlShowing) {
            DialogItemAddTypeUrl(viewModel = viewModel)
        }
        if (uiState.isAppShowing) {
            Spacer(Modifier.size(padding.extraSmall))
            DialogItemAddTypeApp(
                viewModel = viewModel,
                onClick = { onSave(viewModel.saveApp(it)) }
            )
        }
        DialogButtonsDone(
            modifier = Modifier.padding(
                top = if (uiState.isUrlShowing) padding.small else padding.extraSmall,
                bottom = padding.extraSmall,
                start = padding.extraSmall,
                end = padding.extraSmall
            ),
            onSave = if (uiState.isUrlShowing) {
                { onSave(viewModel.saveUrl()) }
            } else {
                null
            },
            onCancel = onCancel
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
fun DialogItemAddLandscape(
    onSave: (MediaItem) -> Unit,
    onCancel: () -> Unit,
    viewModel: DialogItemAddViewModel,
    uiState: DialogItemAddUiState
) {
    Row(
        Modifier
            .fillMaxWidth()
    ) {
        val padding = Dimens.padding

        Column(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .padding(
                    horizontal = padding.medium,
                    vertical = padding.large
                )
        ) {
            Title()
            Spacer(Modifier.size(padding.medium))
            MediaTypeSelector(
                onSelected = {
                    when (it) {
                        MediaItemType.APP -> viewModel.showApp()
                        MediaItemType.URL -> viewModel.showUrl()
                        else -> {
                            // do nothing
                        }
                    }
                },
                selectedType = viewModel.selectedType
            )
        }
        Column(
            modifier = Modifier
                .padding(
                    start = padding.medium,
                    end = padding.medium,
                    top = padding.large,
                    bottom = padding.small
                )
        ) {
            if (uiState.isUrlShowing) {
                DialogItemAddTypeUrl(viewModel = viewModel)
            }
            if (uiState.isAppShowing) {
                DialogItemAddTypeApp(
                    viewModel = viewModel,
                    onClick = { onSave(viewModel.saveApp(it)) }
                )
            }
            DialogButtonsDone(
                modifier = Modifier.padding(
                    top = if (uiState.isUrlShowing) padding.small else padding.extraSmall,
                    bottom = padding.extraSmall,
                    start = padding.extraSmall,
                    end = padding.extraSmall
                ),
                onSave = if (uiState.isUrlShowing) {
                    { onSave(viewModel.saveUrl()) }
                } else {
                    null
                },
                onCancel = onCancel
            )
        }
    }
}