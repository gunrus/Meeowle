package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.favourites

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.LoadingCatPhoto
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.shared.favourites.FavouriteCat

private val CAT_NAME_BOX_WIDTH = 110.dp
private val CAT_NAME_BOX_HEIGHT = 36.dp
private val CAT_NAME_TEXT_WIDTH = 90.dp
private val CARD_WIDTH = 150.dp
private val CARD_HEIGHT = 180.dp

/**
 * @author Ruslan Ganeev
 */
@Composable
fun FavouriteCatCard(
    modifier: Modifier = Modifier,
    favouriteCat: FavouriteCat,
    onCatClicked: (Cat) -> Unit
) {
    Box(
        modifier = modifier
            .width(CARD_HEIGHT)
            .height(CARD_HEIGHT + CAT_NAME_BOX_HEIGHT / 2)
            .clickable {
                onCatClicked(favouriteCat.cat)
            }
            .testTag("favouriteCatCard")
    ) {
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .width(CARD_HEIGHT)
                .height(CARD_HEIGHT)
        ) {
            LoadingCatPhoto(
                model = favouriteCat.catPhoto,
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(CAT_NAME_BOX_HEIGHT)
                .width(CAT_NAME_BOX_WIDTH)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = favouriteCat.cat.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(CAT_NAME_TEXT_WIDTH)
                    .align(Alignment.Center)
                    .testTag("favouriteCatName")
            )
        }
    }

}
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun NewCatCardDarkPreview() {
    val cat = Cat(
        id = 1,
        name = "МурзикМурзикМурзикМурзикМурзик",
        description = "AaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaa",
        gender = Gender.UNISEX,
        likes = 100,
        dislikes = 100,
    )
    val favouriteCat = FavouriteCat(
        cat = cat,
        catPhoto = null
    )
    MeowleTheme {
        FavouriteCatCard(favouriteCat = favouriteCat) {

        }
    }
}

@Composable
@Preview
fun NewCatCardLightPreview() {
    val cat = Cat(
        id = 1,
        name = "МурзикМурзикМурзикМурзикМурзик",
        description = "AaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaa",
        gender = Gender.UNISEX,
        likes = 100,
        dislikes = 100,
    )
    val favouriteCat = FavouriteCat(
        cat = cat,
        catPhoto = null
    )
    MeowleTheme {
        FavouriteCatCard(favouriteCat = favouriteCat) {

        }
    }
}
