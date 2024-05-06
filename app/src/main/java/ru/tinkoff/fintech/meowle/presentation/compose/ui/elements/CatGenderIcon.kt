package ru.tinkoff.fintech.meowle.presentation.compose.ui.elements

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Gender

/**
 * @author Ruslan Ganeev
 */
@Composable
fun CatGenderIcon(gender: Gender) {
    val painter: Painter
    val contentDescription: String
    when (gender) {
        Gender.MALE -> {
            painter = painterResource(R.drawable.gender_male)
            contentDescription = stringResource(R.string.cat_gender_male_content_description)
        }
        Gender.FEMALE -> {
            painter = painterResource(R.drawable.gender_female)
            contentDescription = stringResource(R.string.cat_gender_female_content_description)
        }
        Gender.UNISEX -> {
            painter = painterResource(R.drawable.gender_unisex)
            contentDescription = stringResource(R.string.cat_gender_unisex_content_description)
        }
    }

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onSurface
    )
}
