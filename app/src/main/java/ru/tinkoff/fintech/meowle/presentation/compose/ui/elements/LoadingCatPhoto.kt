package ru.tinkoff.fintech.meowle.presentation.compose.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.shimmerBrush
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing

/**
 * @author Ruslan Ganeev
 */
@Composable
fun LoadingCatPhoto(
    modifier: Modifier = Modifier,
    model: Any?
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(model)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.favourites_cat_photo_description),
        loading = {
            Spacer(
                modifier = Modifier
                    .background(shimmerBrush)
            )
        },
        error = {
            Image(
                painter = painterResource(id = R.drawable.sleepy_cat),
                contentDescription = stringResource(R.string.favourites_cat_photo_description),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .padding(MaterialTheme.spacing.large)
            )
        },
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
    )
}