package io.smileyjoe.media.ui.component.dialog.item_add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.smileyjoe.media.ui.theme.Dimens
import io.smileyjoe.packages.objects.PackageInfo

@Composable
fun ColumnScope.DialogItemAddTypeApp(
    viewModel: DialogItemAddViewModel,
    onClick: (PackageInfo) -> Unit
) {
    AppList(
        viewModel = viewModel,
        onClick = onClick
    )
}

@Composable
private fun ColumnScope.AppList(
    viewModel: DialogItemAddViewModel,
    onClick: (PackageInfo) -> Unit
) {
    val padding = Dimens.padding
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(
            start = padding.medium,
            end = padding.medium,
            bottom = padding.small,
            top = padding.small
        ),
        verticalArrangement = Arrangement.spacedBy(padding.medium),
        state = listState
    ) {
        items(viewModel.getPackages()) { packageInfo ->
            DialogItemAddPackageListItem(
                packageInfo = packageInfo,
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onClick(packageInfo)
                }
            )
        }
    }
}