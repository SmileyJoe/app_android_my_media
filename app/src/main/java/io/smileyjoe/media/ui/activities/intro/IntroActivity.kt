package io.smileyjoe.media.ui.activities.intro

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import io.smileyjoe.media.R
import io.smileyjoe.media.lottie.LottieFileSelect
import io.smileyjoe.media.ui.activities.main.MainActivity
import io.smileyjoe.media.ui.component.dialog.confirm.DialogConfirm
import io.smileyjoe.media.ui.component.dialog.error.DialogError
import io.smileyjoe.media.ui.component.dialog.loading.DialogLoading
import io.smileyjoe.media.ui.component.lottie.LottieImage
import io.smileyjoe.media.ui.theme.Dimens
import io.smileyjoe.media.ui.theme.MyMediaTheme

class IntroActivity : ComponentActivity() {

    val mimeType = "application/json"
    val fileNameDefault = "my_media.json"
    val viewModel: IntroActivityViewModel by viewModels()

    val createFile = registerForActivityResult(CreateDocument(mimeType)) {
        viewModel.fileCreated(it)
    }

    val openFile = registerForActivityResult(OpenDocument()) {
        viewModel.fileLoaded(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        splashScreen.setKeepOnScreenCondition{ viewModel.uiState.value.showSplash }
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
        val viewModel: IntroActivityViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsState()
        val errorMessage by viewModel.errorMessage
        val fileInfo by viewModel.fileInfo

        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            if (uiState.isFileSaved) {
                startActivity(MainActivity.getIntent(baseContext))
                finish()
            } else {
                if (uiState.showChooseFile) {
                    when (LocalConfiguration.current.orientation) {
                        Configuration.ORIENTATION_LANDSCAPE -> ChooseFileLandscape()
                        else -> ChooseFilePortrait()
                    }
                }

                if (uiState.showError) {
                    DialogError(
                        messageResId = errorMessage,
                        onDismiss = { viewModel.hideError() }
                    )
                }

                if (uiState.showLoading) {
                    DialogLoading(
                        onDismiss = { viewModel.hideLoading() }
                    )
                }

                if (uiState.showConfirmFile) {
                    DialogConfirm(
                        onDismiss = {
                            viewModel.hideConfirmFile()
                        },
                        message = fileInfo?.name,
                        onPositive = {
                            viewModel.saveFile()
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun BoxScope.ChooseFilePortrait() {
        val padding = Dimens.padding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = padding.medium,
                    end = padding.medium,
                    bottom = padding.large,
                    top = padding.large
                ),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            LottieImage(
                image = LottieFileSelect(
                    color = MaterialTheme.colorScheme.primary,
                    colorBackground = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .widthIn(0.dp, dimensionResource(R.dimen.lottie_full_width_max))
            )
            ChooseFileContent()
        }
    }

    @Composable
    fun BoxScope.ChooseFileLandscape() {
        val padding = Dimens.padding
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = padding.extraLarge, end = padding.medium, bottom = padding.large)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
            ) {
                LottieImage(
                    image = LottieFileSelect(
                        color = MaterialTheme.colorScheme.primary,
                        colorBackground = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .widthIn(0.dp, dimensionResource(R.dimen.lottie_full_width_max))
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = padding.medium,
                        end = padding.extraLarge,
                        bottom = padding.large,
                        top = padding.large
                    ),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                ChooseFileContent()
            }
        }
    }

    @Composable
    fun ChooseFileContent() {
        val padding = Dimens.padding

        Text(
            text = stringResource(R.string.instruction_choose_file),
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    createFile.launch(fileNameDefault)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.button_create_file))
            }

            Button(
                onClick = {
                    openFile.launch(arrayOf(mimeType))
                },
                modifier = Modifier
                    .padding(top = padding.medium)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.button_load_file))
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun ScreenPreview() {
        Screen()
    }
}