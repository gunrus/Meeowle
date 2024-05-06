package ru.tinkoff.fintech.meowle.presentation.compose.ui.elements

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing

/**
 * @author Ruslan Ganeev
 */
@Composable
fun PawBox(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.paw),
            contentDescription = stringResource(R.string.cat_paw_content_description),
            tint = MaterialTheme.colorScheme.surfaceDim,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(MaterialTheme.spacing.large)
        )
        content()
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun MeowleBottomBarDarkPreview() {
    MeowleTheme {
        PawBox {  }
    }
}

@Composable
@Preview(
    showBackground = true
)
private fun MeowleBottomBarLightPreview() {
    MeowleTheme {
        PawBox {  }
    }
}