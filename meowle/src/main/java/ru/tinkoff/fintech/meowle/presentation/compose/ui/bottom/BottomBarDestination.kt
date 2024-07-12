package ru.tinkoff.fintech.meowle.presentation.compose.ui.bottom

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.generated.destinations.AddCatScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FavouritesScreenDestination
import com.ramcosta.composedestinations.generated.destinations.RatingScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.generated.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    @StringRes
    val title: Int,
    val icon: ImageVector,
) {
    SEARCH(SearchScreenDestination, R.string.search, Icons.Default.Search),
    RATING(RatingScreenDestination, R.string.rating, Icons.Default.Star),
    ADD_CAT(AddCatScreenDestination, R.string.add_bottom_bar, Icons.Default.Add),
    FAVOURITES(FavouritesScreenDestination, R.string.favourites, Icons.Default.Favorite),
    SETTINGS(SettingsScreenDestination, R.string.settings, Icons.Default.Settings)
}
