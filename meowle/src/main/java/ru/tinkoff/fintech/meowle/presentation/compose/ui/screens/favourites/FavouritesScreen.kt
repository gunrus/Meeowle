package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.favourites

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CatDetailsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.ContentPlaceholder
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.PawBox
import ru.tinkoff.fintech.meowle.presentation.compose.ui.lazyListItemPosition
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing
import ru.tinkoff.fintech.meowle.presentation.shared.favourites.FavouriteCat
import ru.tinkoff.fintech.meowle.presentation.shared.favourites.FavouritesViewModel

/**
 * @author Ruslan Ganeev
 */
@Composable
@Destination<RootGraph>
fun FavouritesScreen(
    viewModel: FavouritesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    LaunchedEffect(Unit) {
        viewModel.loadFavouriteCats()
        viewModel.isCatsLoaded.collectLatest {
            viewModel.loadPhotos()
        }
    }

    val favouriteCats = viewModel.favouriteCats.collectAsStateWithLifecycle().value

    if (favouriteCats.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            ContentPlaceholder(
                imagePainter = painterResource(R.drawable.sad_cat),
                title = stringResource(R.string.favourites_cats_empty_content_description)
            )
        }
    } else {
        FavouritesList(favouriteCats) {
            navigator.navigate(CatDetailsScreenDestination(it))
        }
    }
}



@Composable
fun FavouritesList(
    favouriteCats: List<FavouriteCat>,
    onCatClicked: (Cat) -> Unit
) {
    PawBox {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium,
                    bottom = MaterialTheme.spacing.medium
                )
                .testTag("favouritesCatsList")
        ) {
            itemsIndexed(favouriteCats) { index, favouriteCat ->
                FavouriteCatCard(
                    favouriteCat = favouriteCat,
                    onCatClicked = onCatClicked,
                    modifier = Modifier
                        .lazyListItemPosition(index)
                )
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun FavouritesListPreview() {
    MeowleTheme {
        val favouriteCat = FavouriteCat(
            cat = Cat(
                id = 1,
                name = "МурзикМурзикМурзикМурзикМурзик",
                description = "Fff",
                gender = Gender.UNISEX,
                likes = 100,
                dislikes = 100,
            ),
            catPhoto = null
        )

        val favouriteCats = listOf(favouriteCat, favouriteCat, favouriteCat)
        FavouritesList(favouriteCats) {

        }
    }
}
