package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.rating

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Vote
import ru.tinkoff.fintech.meowle.presentation.compose.ui.dropShadow
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme

private val RATING_TAB_HEIGHT = 50.dp

/**
 * @author Ruslan Ganeev
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RatingTabs(
    modifier: Modifier = Modifier,
    selectedTab: Vote,
    onTabSelect: (Vote) -> Unit
) {
    SecondaryTabRow(
        containerColor = MaterialTheme.colorScheme.surfaceDim,
        selectedTabIndex = selectedTab.toSelected(),
        modifier = modifier
            .dropShadow(
                shape = RectangleShape,
                offsetX = 0.dp,
                offsetY = 4.dp,
                blur = 4.dp,
            )
    ) {
        RatingTab(
            vote = Vote.LIKES,
            onTabClick = onTabSelect,
        )
        RatingTab(
            vote = Vote.DISLIKES,
            onTabClick = onTabSelect,
        )
    }
}

@Composable
private fun RatingTab(
    vote: Vote,
    onTabClick: (Vote) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(RATING_TAB_HEIGHT)
    ) {
        val title = when (vote) {
            Vote.LIKES -> stringResource(id = R.string.rating_tab_likes_title)
            Vote.DISLIKES -> stringResource(id = R.string.rating_tab_dislikes_title)
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .clickable { onTabClick(vote) }
        )
    }
}

private fun Vote.toSelected(): Int {
    return when (this) {
        Vote.LIKES -> 0
        Vote.DISLIKES -> 1
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RatingTabPreview() {
    MeowleTheme {
        RatingTab(
            vote = Vote.LIKES,
            onTabClick = {}
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RatingTabsPreview() {
    MeowleTheme {
        RatingTabs(
            selectedTab = Vote.LIKES,
            onTabSelect = {}
        )
    }
}
