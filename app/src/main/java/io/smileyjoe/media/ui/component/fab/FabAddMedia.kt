package io.smileyjoe.media.ui.component.fab

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import io.smileyjoe.media.ui.theme.Dimens

class FabAddMedia private constructor(
    private val viewModel: FabAddMediaViewModel
) {
    companion object {
        @Composable
        fun controller() =
            FabAddMedia(viewModel())
    }

    fun expand() =
        viewModel.expand()
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FabAddMedia(
    modifier: Modifier,
    onClickOption: (FabAddMediaOption) -> Unit
) {
    val viewModel: FabAddMediaViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isShowing) {
        FloatingActionButtonMenu(
            expanded = uiState.isExpanded,
            button = {
                ToggleFloatingActionButton(
                    checked = uiState.isExpanded,
                    onCheckedChange = {
                        viewModel.toggleExpanded()
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
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun FloatingActionButtonMenuScope.FabAddOption(
    option: FabAddMediaOption,
    onClick: (FabAddMediaOption) -> Unit
) {
    val viewModel: FabAddMediaViewModel = viewModel()
    val padding = Dimens.padding

    FloatingActionButtonMenuItem(
        icon = { Icon(option.icon, contentDescription = null) },
        text = { Text(text = stringResource(id = option.titleRes)) },
        onClick = {
            viewModel.contract()
            onClick(option)
        },
        modifier = Modifier.padding(bottom = padding.small)
    )
}