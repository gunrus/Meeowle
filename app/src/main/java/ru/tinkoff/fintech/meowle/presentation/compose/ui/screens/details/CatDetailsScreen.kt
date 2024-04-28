package ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.details

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Cat
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.compose.ui.screens.shimmerBrush
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.MeowleTheme
import ru.tinkoff.fintech.meowle.presentation.compose.ui.theme.spacing
import ru.tinkoff.fintech.meowle.presentation.shared.details.CatDetailsState
import ru.tinkoff.fintech.meowle.presentation.shared.details.CatDetailsViewModel

/**
 * @author Ruslan Ganeev
 */
@Composable
@Destination<RootGraph>
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun CatDetailsScreen(
    cat: Cat,
    viewModel: CatDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.updateCat(cat)
        viewModel.loadCatDetails(cat)
    }

    val cat = viewModel.cat.collectAsStateWithLifecycle().value
    val catDetailsState = viewModel.catDetailsState.collectAsStateWithLifecycle().value
    val bottomSheetState = viewModel.bottomSheetState.collectAsStateWithLifecycle().value

    Scaffold(
        floatingActionButton = {
            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = viewModel::onNewCatPhotoSelected
            )

            FloatingActionButton(
                onClick = {
                    val mimeType = "image/*"
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType))
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.large)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.details_fab_content_description)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        CatDetailsContent(
            cat = cat,
            catDetails = catDetailsState,
            onVote = viewModel::onVoteClicked,
            onFavoriteClick = viewModel::onFavoriteClicked,
            onEditButtonClicked = viewModel::onEditCatDescriptionClicked
        )
        if (bottomSheetState.isShown) {
            CatDetailsBottomSheet(
                catDescription = bottomSheetState.catDescription,
                onCatDescriptionChange = viewModel::onCatDescriptionChange,
                onSaveCatDescription = viewModel::onSaveDescriptionClicked,
                onClose = viewModel::onCloseBottomSheet
            )
        }
    }
}

@Composable
fun CatDetailsContent(
    cat: Cat,
    catDetails: CatDetailsState,
    onVote: (Boolean) -> Unit,
    onFavoriteClick: () -> Unit,
    onEditButtonClicked: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = Modifier
            .padding(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                bottom = MaterialTheme.spacing.medium
            )
    ) {

        item(span = {
            GridItemSpan(maxLineSpan)
        }) {
            CatDetailsCard(
                cat = cat,
                catAvatarUrl = catDetails.catPhotoUrls.firstOrNull(),
                isVoted = catDetails.vote,
                onVote = onVote,
                isFavorite = catDetails.isFavorite,
                onFavoriteClick = onFavoriteClick,
                onEditButtonClicked = onEditButtonClicked
            )
        }

        item(span = {
            GridItemSpan(maxLineSpan)
        }) {
            Text(
                text = stringResource(R.string.details_photos_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.spacing.large,
                        bottom = MaterialTheme.spacing.large
                    )
            )
        }

        if (catDetails.catPhotoUrls.isEmpty()) {
            item(span = {
                GridItemSpan(maxLineSpan)
            }) {
                Text(
                    text = stringResource(R.string.details_photos_empty_description),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(catDetails.catPhotoUrls) {
            SubcomposeAsyncImage(
                model = it,
                contentDescription = stringResource(R.string.favourites_cat_photo_description),
                loading = {
                    Spacer(
                        modifier = Modifier
                            .background(shimmerBrush)
                    )
                },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.cat),
                        contentDescription = stringResource(R.string.favourites_cat_photo_description),
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                    )
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }
}

@Composable
private fun CatDetailsScreenPreview() {
    val cat = Cat(
        id = 1,
        name = "Мурзик",
        description = "AaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaaAaaaaaaaaaaa",
        gender = Gender.UNISEX,
        likes = 100,
        dislikes = 100,
    )

    MeowleTheme {

    }
}
