package com.dvt.weatherapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.dvt.weatherapp.R

val JosefinSans = FontFamily(
    Font(R.font.josefinsans_bold),
    Font(R.font.josefinsans_semibold),
    Font(R.font.josefinsans_regular),
    Font(R.font.josefinsans_italic),
    Font(R.font.josefinsans_light)
)

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Bold,
        fontSize = 60.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.White,
    ),
    headlineMedium = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = Color.White,
    ),
    headlineSmall = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.Black,
    ),
    titleMedium = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.White,
    ),
    titleSmall = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.White,
    ),
    labelMedium = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        textDecoration = TextDecoration.Underline,
        lineHeight = 24.sp,
        color = Color.White,
    ),
    labelSmall = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Italic,
        fontSize = 13.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.White,
    ),
    displayLarge = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = Color.White,
    ),
    displayMedium = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = Color.White,
    ),
    displaySmall = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 12.sp,
        lineHeight = 24.sp,
        color = Color.White,
    ),
)