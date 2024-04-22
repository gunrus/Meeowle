package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.shimmerBrush

/**
 * @author Ruslan Ganeev
 */
@Composable
fun SearchCatCardShimmer(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(brush = shimmerBrush)
    )
}

@Preview
@Composable
fun CatsListShimmerPreview() {
    MaterialTheme {
        SearchCatCardShimmer()
    }
}