package io.smileyjoe.media.ui.component.dialog.item_add

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import io.smileyjoe.media.models.MediaItemType
import io.smileyjoe.media.ui.theme.Dimens

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MediaTypeSelector(
    onSelected: (type: MediaItemType) -> Unit,
    selectedType: MediaItemType
) {
    val options = MediaItemType.entries.filter { it.showInTab }

    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> MediaTypeSelectorLandscape(
            onSelected = {
                onSelected(it)
            },
            options = options,
            selectedType = selectedType
        )

        else -> MediaTypeSelectorPortrait(
            onSelected = onSelected,
            options = options,
            selectedType = selectedType
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun MediaTypeButton(
    type: MediaItemType,
    selectedType: MediaItemType
) {
    val padding = Dimens.padding
    Icon(
        if (selectedType == type) type.iconSelected else type.iconUnSelected,
        contentDescription = stringResource(type.title),
    )
    Spacer(Modifier.size(padding.medium))
    Text(
        text = stringResource(type.title)
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun MediaTypeSelectorLandscape(
    onSelected: (type: MediaItemType) -> Unit,
    options: List<MediaItemType>,
    selectedType: MediaItemType
) {
    options.forEach { type ->
        ToggleButton(
            modifier = Modifier.fillMaxWidth(),
            checked = selectedType == type,
            onCheckedChange = {
                onSelected(type)
            }
        ) {
            MediaTypeButton(
                type = type,
                selectedType = selectedType
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun MediaTypeSelectorPortrait(
    onSelected: (type: MediaItemType) -> Unit,
    options: List<MediaItemType>,
    selectedType: MediaItemType
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)
    ) {
        val modifiers = listOf(Modifier.weight(1f), Modifier.weight(1.5f))

        options.forEachIndexed { index, type ->
            ToggleButton(
                checked = selectedType == type,
                onCheckedChange = {
                    onSelected(type)
                },
                modifier = modifiers[index].semantics { role = Role.RadioButton },
                shapes =
                    when (index) {
                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    },
            ) {
                MediaTypeButton(
                    type = type,
                    selectedType = selectedType
                )
            }
        }
    }
}