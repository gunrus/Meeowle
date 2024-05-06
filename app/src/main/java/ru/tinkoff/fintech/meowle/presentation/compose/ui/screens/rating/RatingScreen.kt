package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.rating

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CatDetailsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.domain.cat.Vote
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.PawBox
import ru.tinkoff.fintech.meowle.presentation.compose.ui.lazyListItemPosition
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing
import ru.tinkoff.fintech.meowle.presentation.shared.rating.RatingViewModel

/**
 * @author Ruslan Ganeev
 */
@Composable
@Destination<RootGraph>
fun RatingScreen(
    viewModel: RatingViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.loadRating()
    }

    val state = viewModel.catsRating.collectAsStateWithLifecycle().value

    RatingScreenContent(
        selectedTab = state.vote,
        cats = state.cats,
        onTabSelect = { viewModel.loadRating(it) },
        onCatClick = { navigator.navigate(CatDetailsScreenDestination(it)) }
    )
}

@Composable
private fun RatingScreenContent(
    selectedTab: Vote,
    cats: List<Cat>,
    onTabSelect: (Vote) -> Unit,
    onCatClick: (Cat) -> Unit
) {
    PawBox {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RatingTabs(
                selectedTab = selectedTab,
                onTabSelect = onTabSelect
            )

            LazyColumn(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly,
                contentPadding = PaddingValues(
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium
                )
            ) {
                itemsIndexed(cats) { index, cat ->
                    RatingCatCard(
                        cat = cat,
                        position = index + 1,
                        vote = selectedTab,
                        onCatClicked = onCatClick,
                        modifier = Modifier
                            .lazyListItemPosition(index)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RatingScreenPreview() {
    val cat = Cat(
        id = 1,
        name = "Мурзик",
        description = "Sd",
        gender = Gender.MALE,
        likes = 10,
        dislikes = 10
    )

    val cats = listOf(cat, cat, cat)
    MeowleTheme {
        RatingScreenContent(
            selectedTab = Vote.LIKES,
            cats = cats,
            onTabSelect = {},
            onCatClick = {}
        )
    }
}
