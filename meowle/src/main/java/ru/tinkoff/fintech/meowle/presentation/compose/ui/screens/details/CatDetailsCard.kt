package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.details

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.CatGenderIcon
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.LoadingCatPhoto
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing

/**
 * @author Ruslan Ganeev
 */
@Composable
fun CatDetailsCard(
    cat: Cat,
    catAvatarUrl: Uri?,
    isVoted: Boolean?,
    onVote: (Boolean) -> Unit,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onEditButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceDim
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.extraSmall,
                    top = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.none
                )
        ) {
            Row {
                Text(
                    text = "${cat.name}, ",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                CatGenderIcon(gender = cat.gender)
            }
            IconButton(
                onClick = {
                    onFavoriteClick()
                }
            ) {
                val favouriteImageVector = if (isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                }
                Icon(
                    imageVector = favouriteImageVector,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

        }
        LoadingCatPhoto(
            model = catAvatarUrl,
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .padding(
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium
                )
                .clip(MaterialTheme.shapes.large)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium
                )
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(0.6f)
                    .padding(
                        top = MaterialTheme.spacing.medium,
                        bottom = MaterialTheme.spacing.medium,
                        end = MaterialTheme.spacing.medium
                    )
            ) {
                val description = cat.description.ifEmpty {
                    stringResource(R.string.details_empty_description)
                }
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.small)
                )
                Button(
                    onClick = onEditButtonClicked,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(R.string.details_change_description_button),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Votes(
                likes = cat.likes,
                dislikes = cat.dislikes,
                isVoted = isVoted,
                onVote = onVote,
                modifier = Modifier
                    .weight(0.4f)
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
private fun NewCatCardDarkPreview() {
    val cat = Cat(
        id = 1,
        name = "Мурзик",
        description = "AaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaa",
        gender = Gender.UNISEX,
        likes = 100,
        dislikes = 100,
    )
    MeowleTheme {
        CatDetailsCard(
            cat = cat,
            catAvatarUrl = null,
            isVoted = null,
            onVote = {},
            isFavorite = true,
            onFavoriteClick = {},
            onEditButtonClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun NewCatCardLightPreview() {
    val cat = Cat(
        id = 1,
        name = "Мурзик",
        description = "AaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaa",
        gender = Gender.UNISEX,
        likes = 100,
        dislikes = 100,
    )
    MeowleTheme {
        CatDetailsCard(
            cat = cat,
            catAvatarUrl = null,
            isVoted = null,
            onVote = {},
            isFavorite = true,
            onFavoriteClick = {},
            onEditButtonClicked = {}
        )
    }
}
