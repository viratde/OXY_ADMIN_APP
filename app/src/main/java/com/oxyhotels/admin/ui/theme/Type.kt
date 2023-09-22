package com.oxyhotels.admin.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.R

val robotoMono = FontFamily(
    Font(R.font.robotomono_medium,FontWeight.Medium),
    Font(R.font.robotomono_semibold,FontWeight.SemiBold),
)

val mplus = FontFamily(
    Font(R.font.mplus_extrabold,FontWeight.ExtraBold),
)


// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = mplus,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 48.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = mplus,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 37.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = mplus,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 23.sp,
    ),

    bodyMedium = TextStyle(
        fontFamily = mplus,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),

    bodySmall = TextStyle(
        color = secondary
    ),


    titleMedium = TextStyle(
        fontFamily = robotoMono,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = robotoMono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
    ),
    titleLarge = TextStyle(
        color = secondary
    ),
    bodyLarge = TextStyle(
        color = secondary
    ),
    labelSmall = TextStyle(
        color = secondary
    ),
    labelMedium = TextStyle(
        color = secondary
    ),
    labelLarge = TextStyle(
        color = secondary
    )
)