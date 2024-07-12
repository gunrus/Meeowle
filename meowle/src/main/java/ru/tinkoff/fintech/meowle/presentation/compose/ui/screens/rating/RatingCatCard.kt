package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.rating

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.domain.cat.Vote
import ru.tinkoff.fintech.meowle.presentation.compose.ui.dropShadow
import ru.tinkoff.fintech.meowle.presentation.compose.ui.innerShadow
import ru.tinkoff.fintech.meowle.presentation.compose.ui.mirror
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing

private val TOTAL_HEIGHT = 80.dp
private val RATING_CAT_CARD_HEIGHT = 67.dp
private val ICON_SIZE = 30.dp
private val CAT_NAME_WIDTH = 140.dp

/**
 * @author Ruslan Ganeev
 */
@Composable
fun RatingCatCard(
    modifier: Modifier = Modifier,
    cat: Cat,
    position: Int,
    vote: Vote,
    onCatClicked: (Cat) -> Unit
) {
    Box(
        modifier = modifier
            .height(TOTAL_HEIGHT)
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceDim
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(RATING_CAT_CARD_HEIGHT)
                .align(Alignment.BottomCenter)
                .dropShadow(
                    shape = MaterialTheme.shapes.medium,
                    offsetX = 2.dp,
                    offsetY = 4.dp,
                    blur = 4.dp,
                )
                .innerShadow(
                    shape = MaterialTheme.shapes.medium,
                    offsetX = 2.dp,
                    offsetY = 4.dp,
                    blur = 4.dp,
                    spread = 0.dp
                )
                .clickable { onCatClicked(cat) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Text(
                        text = "$position.",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = cat.name,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .width(CAT_NAME_WIDTH)
                            .padding(start = MaterialTheme.spacing.medium)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    val iconModifier: Modifier
                    val votesCount: Int
                    when (vote) {
                        Vote.LIKES -> {
                            iconModifier = Modifier
                            votesCount = cat.likes
                        }
                        Vote.DISLIKES -> {
                            iconModifier = Modifier
                                .mirror()
                                .rotate(180f)
                            votesCount = cat.dislikes
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = iconModifier
                            .padding(end = MaterialTheme.spacing.medium)
                            .size(ICON_SIZE)
                    )
                    Text(
                        text = votesCount.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
        if (position in 1..3 && vote == Vote.LIKES) {
            val crown = when (position) {
                1 -> ImageVector.vectorResource(R.drawable.crown_gold)
                2 -> ImageVector.vectorResource(R.drawable.crown_silver)
                else -> ImageVector.vectorResource(R.drawable.crown_bronze)
            }

            Image(
                imageVector = crown,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(ICON_SIZE)
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RatingCatCardPreview() {
    val cat = Cat(
        id = 1,
        name = "Мурзииииииииииииииииииииик",
        description = "Мурзик",
        gender = Gender.MALE,
        likes = 100,
        dislikes = 100,
    )

    MeowleTheme {
        RatingCatCard(
            modifier = Modifier,
            cat = cat,
            position = 1,
            vote = Vote.LIKES
        ) {}
    }
}
