package io.smileyjoe.packages.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import io.smileyjoe.packages.drawable.IconLetter
import io.smileyjoe.packages.objects.PackageInfo

@Preview
@Composable
fun PackageIconPreview() {
    PackageIcon(
        details = PackageInfo(
            id = "io.smileyjoe.example",
            name = "Example",
            icon = null,
            isInstalled = true
        ),
        modifier = Modifier.size(24.dp)
    )
}

@Composable
fun PackageIcon(
    details: PackageInfo,
    modifier: Modifier
) {
    Image(
        painter = rememberDrawablePainter(
            drawable = details.icon ?: IconLetter(
                text = details.name,
                characters = 2
            )
        ),
        contentDescription = details.name,
        modifier = modifier
    )
}

