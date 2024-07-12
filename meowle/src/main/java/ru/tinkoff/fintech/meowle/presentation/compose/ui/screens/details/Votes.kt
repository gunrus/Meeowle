package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.fintech.meowle.presentation.compose.ui.mirror
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme

private val THUMBS_UP_ICON_SIZE = 30.dp

/**
 * @author Ruslan Ganeev
 */
@Composable
fun VoteElement(
    modifier: Modifier = Modifier,
    votes: Int,
    isLike: Boolean,
    isVoted: Boolean,
    onVote: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val iconModifier = if (isLike) {
            Modifier
        } else {
            Modifier
                .mirror()
                .rotate(180f)
        }
        IconButton(
            onClick = {
                onVote(isLike)
            },
            enabled = !isVoted,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = "",
                modifier = iconModifier
                    .size(THUMBS_UP_ICON_SIZE)
            )
        }
        Text(
            text = votes.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun Votes(
    modifier: Modifier = Modifier,
    likes: Int,
    dislikes: Int,
    isVoted: Boolean?,
    onVote: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
    ) {
        val liked: Boolean
        val disliked: Boolean

        when (isVoted) {
            true -> {
                liked = true
                disliked = false
            }
            false -> {
                liked = false
                disliked = true
            }
            null -> {
                liked = false
                disliked = false
            }
        }

        VoteElement(
            votes = likes,
            isLike = true,
            isVoted = liked,
            onVote = onVote
        )
        VoteElement(
            votes = dislikes,
            isLike = false,
            isVoted = disliked,
            onVote = onVote
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun VoteElementDarkPreview() {
    MeowleTheme {
        VoteElement(
            votes = 100,
            isLike = true,
            isVoted = false
        ) {

        }
    }
}

@Composable
@Preview
fun VoteElementLightPreview() {
    MeowleTheme {
        VoteElement(
            votes = 100,
            isLike = true,
            isVoted = false
        ) {

        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun VotesDarkPreview() {
    MeowleTheme {
        Votes(
            likes = 100,
            dislikes = 100,
            isVoted = false
        ) {

        }
    }
}

@Composable
@Preview
private fun VotesLightPreview() {
    MeowleTheme {
        Votes(
            likes = 100,
            dislikes = 100,
            isVoted = false
        ) {

        }
    }
}
