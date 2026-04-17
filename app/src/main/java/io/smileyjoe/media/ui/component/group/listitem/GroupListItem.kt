package io.smileyjoe.media.ui.component.group.listitem

import android.content.pm.PackageManager
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.smileyjoe.media.R
import io.smileyjoe.media.models.Group
import io.smileyjoe.media.models.MediaItem
import io.smileyjoe.media.models.MediaItemType
import io.smileyjoe.media.models.toPackageInfo
import io.smileyjoe.media.ui.theme.Dimens
import io.smileyjoe.packages.ui.component.PackageIcon

//
//@Preview
//@Composable
//fun GroupListItemPreview() {
//    GroupListItem(
//        packageManager = packageManager,
//        group = Group(
//            name = "Preview",
//            items = mutableListOf()
//        ),
//        modifier = Modifier.fillMaxWidth(),
//        onItemClick = {},
//        onNewClick = { group ->
//
//        }
//    )
//}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GroupListItem(
    packageManager: PackageManager,
    group: Group,
    modifier: Modifier,
    onItemClick: (item: MediaItem) -> Unit,
    onNewClick: (group: Group) -> Unit
) {
    val padding = Dimens.padding

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = padding.medium, bottom = padding.medium),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalArrangement = Arrangement.spacedBy(padding.small)
            ) {
                group.items.forEach { item ->
                    MediaItemListItem(
                        packageManager = packageManager,
                        item = item,
                        modifier = Modifier,
                        onClick = { onItemClick(item) }
                    )
                }
                MediaItemListItem(
                    item = MediaItem(
                        title = stringResource(R.string.text_new),
                        type = MediaItemType.NEW,
                        details = stringResource(R.string.text_new)
                    ),
                    modifier = Modifier,
                    onClick = { onNewClick(group) }
                )
            }
        }
    }
}

@Composable
private fun MediaItemListItem(
    packageManager: PackageManager? = null,
    item: MediaItem,
    modifier: Modifier,
    onClick: (MediaItem) -> Unit
) {
    val padding = Dimens.padding
    val iconSize = Dimens.icon
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var modifier = Modifier
            .size(iconSize.large)

        if (item.type != MediaItemType.APP) {
            modifier = modifier.border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = CircleShape
            )
        }
        IconButton(
            modifier = modifier,
            onClick = {
                onClick(item)
            }
        )
        {
            item.toPackageInfo(packageManager)?.let {
                PackageIcon(
                    details = it,
                    modifier = Modifier.size(iconSize.large)
                )
            } ?: run {
                Icon(
                    imageVector = item.type.iconSelected,
                    contentDescription = item.title,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Text(
            text = item.title,
            modifier = Modifier
                .width((iconSize.large.value * 1.5).dp)
                .padding(top = padding.small)
                .clickable(
                    onClick = {
                        onClick(item)
                    }
                ),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}