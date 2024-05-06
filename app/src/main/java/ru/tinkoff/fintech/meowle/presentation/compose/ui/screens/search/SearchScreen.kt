package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.ContentPlaceholder
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.PawBox
import ru.tinkoff.fintech.meowle.presentation.compose.ui.lazyListItemPosition
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing
import ru.tinkoff.fintech.meowle.presentation.shared.search.SearchInputsState
import ru.tinkoff.fintech.meowle.presentation.shared.search.SearchViewModel
import ru.tinkoff.fintech.meowle.presentation.shared.search.Status

/**
 * @author Ruslan Ganeev
 */
@Composable
@Destination<RootGraph>(start = true)
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val inputsState = viewModel.inputState.collectAsStateWithLifecycle().value
    val screenState = viewModel.screenState.collectAsStateWithLifecycle().value

    SearchScreenContent(
        inputsState = inputsState,
        onSearchTextChange = viewModel::onSearchTextChange,
        onSearch = viewModel::onSearch,
        onFilter = {
            viewModel.onBottomSheetExpand(true)
        },
        status = screenState.status,
        cats = screenState.cats,
        onCatClick = {
            navigator.navigate(CatDetailsScreenDestination(it))
        }
    )

    if(screenState.isBottomSheetExpanded) {
        SearchBottomSheet(
            searchOrder = inputsState.searchOrder,
            searchGender = inputsState.searchGender,
            onSearchOrderChange = viewModel::onSearchOrderChange,
            onSearchGenderChange = viewModel::onSearchGenderChange,
            onClose = {
                viewModel.onBottomSheetExpand(false)
            },
            onApply = viewModel::onSearch
        )
    }
}

@Composable
private fun SearchScreenContent(
    inputsState: SearchInputsState,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onFilter: () -> Unit,
    status: Status,
    cats: List<Cat>,
    onCatClick: (Cat) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium
            )
    ) {
        SearchBar(
            searchText = inputsState.searchText,
            isError = !inputsState.isSearchTextValid,
            onSearchTextChange = onSearchTextChange,
            onSearch = onSearch,
            onFilter = onFilter,
        )

        when (status) {
            Status.INITIAL -> InitialContent()
            Status.LOADING -> LoadingContent()
            Status.EMPTY -> EmptyContent()
            Status.ERROR -> {  }
            Status.SUCCESS -> SuccessContent(
                cats = cats,
                onCatClick = onCatClick
            )
        }
    }
}

@Composable
private fun CenterBox(
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        content()
    }
}

@Composable
private fun InitialContent() {
    CenterBox {
        ContentPlaceholder(
            imagePainter = painterResource(R.drawable.sleepy_cat),
            title = stringResource(R.string.search_lets_find_description)
        )
    }
}

@Composable
private fun LoadingContent() {
    PawBox {
        CenterBox {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                repeat(5) {
                    SearchCatCardShimmer(
                        modifier = Modifier
                            .padding(
                                bottom = MaterialTheme.spacing.small
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyContent() {
    CenterBox {
        ContentPlaceholder(
            imagePainter = painterResource(R.drawable.sad_cat),
            title = stringResource(R.string.search_no_cats_found_description)
        )
    }
}

@Composable
private fun SuccessContent(
    cats: List<Cat>,
    onCatClick: (Cat) -> Unit
) {
    PawBox {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(
                bottom = MaterialTheme.spacing.small
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("searchCatsList")
        ) {
            itemsIndexed(cats) { index, cat ->
                SearchCatCard(
                    cat = cat,
                    onCatClick = onCatClick,
                    modifier = Modifier
                        .lazyListItemPosition(index)
                )
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SearchScreenInitialPreview() {
    MeowleTheme {
        SearchScreenContent(
            inputsState = SearchInputsState(),
            onSearchTextChange = {},
            onSearch = { },
            onFilter = { },
            status = Status.INITIAL,
            cats = emptyList()
        ) {  }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SearchScreenLoadingPreview() {
    MeowleTheme {
        SearchScreenContent(
            inputsState = SearchInputsState(),
            onSearchTextChange = {},
            onSearch = { },
            onFilter = { },
            status = Status.LOADING,
            cats = emptyList()
        ) {  }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SearchScreenEmptyPreview() {
    MeowleTheme {
        SearchScreenContent(
            inputsState = SearchInputsState(),
            onSearchTextChange = {},
            onSearch = { },
            onFilter = { },
            status = Status.EMPTY,
            cats = emptyList()
        ) {  }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SearchScreenSuccessPreview() {
    val cat = Cat(
        id = 1,
        name = "Cat",
        description = "Sd",
        gender = Gender.MALE,
        likes = 10,
        dislikes = 10
    )
    val cats = listOf(cat, cat, cat)
    MeowleTheme {
        SearchScreenContent(
            inputsState = SearchInputsState(),
            onSearchTextChange = {},
            onSearch = { },
            onFilter = { },
            status = Status.SUCCESS,
            cats = cats
        ) {  }
    }
}
