package io.smileyjoe.media.ui.component.fab

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.FloatingActionButtonMenuScope
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.animateIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import io.smileyjoe.media.ui.theme.Dimens

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FabAddMedia(
    isExpanded: Boolean,
    modifier: Modifier,
    onClickOption: (option: FabAddMediaOption) -> Unit,
    onStateChanged: (expanded: Boolean) -> Unit
) {
    FloatingActionButtonMenu(
        expanded = isExpanded,
        button = {
            ToggleFloatingActionButton(
                checked = isExpanded,
                onCheckedChange = {
                    Log.d("CheckThings", "Changed: $it")
                    onStateChanged(it)
                }
            ) {
                val icon by remember {
                    derivedStateOf {
                        if (checkedProgress > 0.5f) Icons.Filled.Close else Icons.Filled.Add
                    }
                }
                Icon(
                    painter = rememberVectorPainter(icon),
                    contentDescription = null,
                    modifier = Modifier.animateIcon({ checkedProgress }),
                )

            }
        },
        modifier = modifier
    ) {
        FabAddMediaOption.entries.forEach {
            FabAddOption(
                option = it,
                onClick = onClickOption
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun FloatingActionButtonMenuScope.FabAddOption(
    option: FabAddMediaOption,
    onClick: (FabAddMediaOption) -> Unit
) {
    val padding = Dimens.padding

    FloatingActionButtonMenuItem(
        icon = { Icon(option.icon, contentDescription = null) },
        text = { Text(text = stringResource(id = option.titleRes)) },
        onClick = {
            onClick(option)
        },
        modifier = Modifier.padding(bottom = padding.small)
    )
}