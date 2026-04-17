package io.smileyjoe.media.ui.component.dialog.item_add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.smileyjoe.media.ui.theme.Dimens
import io.smileyjoe.packages.objects.PackageInfo
import io.smileyjoe.packages.ui.component.PackageIcon

@Preview
@Composable
fun DialogItemAddPackageListItemPreview() {
    DialogItemAddPackageListItem(
        packageInfo = PackageInfo(
            id = "io.smileyjoe.preview",
            name = "Preview",
            isInstalled = true,
            icon = null
        ),
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {}
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DialogItemAddPackageListItem(
    packageInfo: PackageInfo,
    modifier: Modifier,
    onClick: (packageInfo: PackageInfo) -> Unit
) {
    val padding = Dimens.padding
    val iconSize = Dimens.icon
    Row(
        modifier = modifier
            .clickable(
                onClick = { onClick(packageInfo) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PackageIcon(
            details = packageInfo,
            modifier = Modifier
                .size(iconSize.medium)
        )
        Text(
            text = packageInfo.name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = padding.small)
                .fillMaxWidth(),
            maxLines = 1
        )
    }
}