package ru.tinkoff.fintech.meowle.presentation.compose.ui.top

import androidx.annotation.StringRes
import com.ramcosta.composedestinations.generated.destinations.AddCatScreenDestination
import com.ramcosta.composedestinations.generated.destinations.CatDetailsScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FavouritesScreenDestination
import com.ramcosta.composedestinations.generated.destinations.RatingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
enum class TopAppBarDestination(
    val direction: DestinationSpec,
    @StringRes
    val title: Int,
    val isNavigateUp: Boolean
) {
    SEARCH(SearchScreenDestination, R.string.search_title, false),
    RATING(RatingScreenDestination, R.string.rating_title, false),
    ADD_CAT(AddCatScreenDestination, R.string.add_title, false),
    FAVOURITES(FavouritesScreenDestination, R.string.favourites_title, false),
    SETTINGS(SettingsScreenDestination, R.string.settings_title, false),
    DETAILS(CatDetailsScreenDestination, R.string.details_title, true)
}