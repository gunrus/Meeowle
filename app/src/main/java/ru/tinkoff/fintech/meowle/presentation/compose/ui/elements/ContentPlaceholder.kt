package ru.tinkoff.fintech.meowle.presentation.compose.ui.elements

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing

private val SMALL_CIRCLE_SIZE = 45.dp
private val LARGE_CIRCLE_SIZE = 256.dp

/**
 * @author Ruslan Ganeev
 */
@Composable
fun ContentPlaceholder(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    title: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val backgroundColor = MaterialTheme.colorScheme.surfaceBright
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(SMALL_CIRCLE_SIZE)
                .background(backgroundColor)

        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .size(LARGE_CIRCLE_SIZE)
                .background(backgroundColor)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = stringResource(R.string.favourites_cats_empty_content_description),
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.medium)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(SMALL_CIRCLE_SIZE)
                .background(backgroundColor)
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
fun ContentPlaceholderNoCatsPreview() {
    MeowleTheme {
        ContentPlaceholder(
            imagePainter = painterResource(R.drawable.sad_cat),
            title = stringResource(R.string.favourites_cats_empty_content_description)
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ContentPlaceholderSearchPreview() {
    MeowleTheme {
        ContentPlaceholder(
            imagePainter = painterResource(R.drawable.sleepy_cat),
            title = stringResource(R.string.search_lets_find_description)
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ContentPlaceholderSearchNotFoundPreview() {
    MeowleTheme {
        ContentPlaceholder(
            imagePainter = painterResource(R.drawable.sad_cat),
            title = stringResource(R.string.search_no_cats_found_description)
        )
    }
}
