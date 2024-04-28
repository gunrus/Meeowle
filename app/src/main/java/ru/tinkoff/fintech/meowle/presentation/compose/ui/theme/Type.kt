package ru.tinkoff.fintech.meowle.presentation.compose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.tinkoff.fintech.meowle.R

/**
 * @author Ruslan Ganeev
 */
private val robotoFontFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.roboto_black, FontWeight.Bold),
    Font(R.font.roboto_black_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_thin_italic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_medium_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.roboto_light, FontWeight.Normal),
    Font(R.font.roboto_light_italic, FontWeight.Normal, FontStyle.Italic)
)

private val tinkoffSansFontFamily = FontFamily(
    Font(R.font.tinkoff_sans_regular, FontWeight.Normal),
    Font(R.font.tinkoff_sans_bold, FontWeight.Bold),
    Font(R.font.tinkoff_sans_medium, FontWeight.Medium)
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = tinkoffSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = tinkoffSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = tinkoffSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = tinkoffSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = tinkoffSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    )
)
