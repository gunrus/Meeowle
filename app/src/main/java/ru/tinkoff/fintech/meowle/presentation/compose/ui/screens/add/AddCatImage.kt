package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.add

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.LoadingCatPhoto
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme

/**
 * @author Ruslan Ganeev
 */
@Composable
fun AddCatImage(
    modifier: Modifier = Modifier,
    catImageUri: Uri?
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(124.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceDim)
    ) {
        if (catImageUri == null) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.add_cat),
                contentDescription = stringResource(R.string.add_photo_content_description),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface)
            )
        } else {
            LoadingCatPhoto(
                model = catImageUri
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AddCatImageDarkPreview() {
    MeowleTheme {
        AddCatImage(
            catImageUri = null
        )
    }
}

@Composable
@Preview
private fun AddCatImageLightPreview() {
    MeowleTheme {
        AddCatImage(
            catImageUri = null
        )
    }
}