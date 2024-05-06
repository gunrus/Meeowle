package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.search

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.compose.ui.dropShadow
import ru.tinkoff.fintech.meowle.presentation.compose.ui.elements.CatGenderIcon
import ru.tinkoff.fintech.meowle.presentation.compose.ui.innerShadow
import ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.details.Votes
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing

/**
 * @author Ruslan Ganeev
 */
@Composable
fun SearchCatCard(
    modifier: Modifier = Modifier,
    cat: Cat,
    onCatClick: (cat: Cat) -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceDim
        ),
        modifier = modifier
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
            .clickable {
                onCatClick(cat)
            }
            .testTag("catCard")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(0.5f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${cat.name}, ",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .testTag("catName")
                    )
                    CatGenderIcon(
                        gender = cat.gender
                    )
                }
                if (cat.description.isNotBlank()) {
                    Text(
                        text = cat.description,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp, //TODO fix line height
                        modifier = Modifier
                            .padding(top = MaterialTheme.spacing.small)
                    )
                }
            }
            Votes(
                likes = cat.likes,
                dislikes = cat.dislikes,
                isVoted = null,
                onVote = {},
                modifier = Modifier
                    .weight(0.5f)
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SearchCatCardPreview() {
    val cat = Cat(
        id = 1,
        name = "Мурзик",
        description = "МурзикМурзикМурзикМурзикМурзикМурзик",
        gender = Gender.MALE,
        likes = 100,
        dislikes = 100,
    )
    MeowleTheme {
        SearchCatCard(cat = cat) {

        }
    }
}
