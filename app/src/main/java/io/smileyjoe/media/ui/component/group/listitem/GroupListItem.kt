package io.smileyjoe.media.ui.component.group.listitem

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.smileyjoe.media.models.Group
import io.smileyjoe.media.models.MediaItem
import io.smileyjoe.media.ui.theme.Dimens

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GroupListItem(
    group: Group,
    modifier: Modifier,
    onNewClick: (group: Group) -> Unit
) {
    val padding = Dimens.padding
    val iconSize = Dimens.icon
    Column(
        modifier = modifier
    ) {
        Text(
            text = group.name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = padding.small)
        )
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            FlowRow(
                modifier = Modifier.padding(all = padding.medium)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(padding.medium)
                ) {
                    items(group.items) { item ->
                        MediaItemListItem(
                            item = item,
                            modifier = Modifier.padding(start = padding.small, end = padding.small)
                        )
                    }
                }
                Column {
                    IconButton(
                        modifier = Modifier
                            .size(iconSize.large)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = CircleShape
                            ),
                        onClick = {
                            onNewClick(group)
                        })
                    {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Text(
                        text = "New",
                        modifier = Modifier
                            .width(iconSize.large)
                            .padding(top = padding.small)
                            .clickable(
                                onClick = { onNewClick(group) }
                            ),
                        textAlign = TextAlign.Center,
                    )
                }

            }
        }
    }
}

@Composable
private fun MediaItemListItem(item: MediaItem, modifier: Modifier) {

}