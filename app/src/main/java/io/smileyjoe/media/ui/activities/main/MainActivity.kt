package io.smileyjoe.media.ui.activities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import io.smileyjoe.media.R
import io.smileyjoe.media.ui.component.dialog.error.DialogError
import io.smileyjoe.media.ui.component.dialog.group_add.DialogGroupAdd
import io.smileyjoe.media.ui.component.dialog.loading.DialogLoading
import io.smileyjoe.media.ui.component.fab.FabAddMedia
import io.smileyjoe.media.ui.component.fab.FabAddMediaOption
import io.smileyjoe.media.ui.theme.Dimens
import io.smileyjoe.media.ui.theme.MyMediaTheme

class MainActivity : ComponentActivity() {

    companion object {
        fun getIntent(context: Context) =
            Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMediaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Screen() {
        val viewModel: MainActivityViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()
        val padding = Dimens.padding
        val groups by viewModel.groups.collectAsState()
        val errorMessage by viewModel.errorMessage
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            Column {
                TopAppBar()
                LazyColumn {
                    items(groups) { group ->
                        Text(text = group.name)
                    }
                }
            }

            if(uiState.isLoading) {
                DialogLoading(
                    onDismiss = { viewModel.hideLoading() }
                )
            }

            if(uiState.isErrorShowing) {
                DialogError(
                    onDismiss = { viewModel.hideError() },
                    messageResId = errorMessage
                )
            }

            if (uiState.isDialogAddGroupShowing) {
                DialogGroupAdd(
                    onSave = { group ->
                        viewModel.apply {
                            addGroup(group)
                            showDialogGroupAdd(false)
                        }
                    },
                    onCancel = {
                        viewModel.showDialogGroupAdd(false)
                    }
                )
            }

            FabAddMedia(
                isExpanded = uiState.isFabAddExpanded,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = padding.medium),
                onClickOption = {
                    viewModel.expandFabAddGroup(false)
                    when (it) {
                        FabAddMediaOption.GROUP -> {
                            viewModel.showDialogGroupAdd(true)
                        }
                    }
                },
                onStateChanged = {
                    viewModel.expandFabAddGroup(it)
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar() {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.title_my_media),
                    color = MaterialTheme.colorScheme.primary
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        // todo: Show Settings
                    })
                {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        )
    }

    @Preview(showBackground = true)
    @Composable
    private fun ScreenPreview() {
        val viewModel: MainActivityViewModel = viewModel()
        viewModel.expandFabAddGroup(true)
        Screen()
    }
}