package com.tusjak.aifin.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tusjak.aifin.R

val robotoFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_bold, FontWeight.Bold))

private val noTrimHeightStyle = LineHeightStyle(LineHeightStyle.Alignment(0.4f), LineHeightStyle.Trim.None)

val headline1     = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Bold,   fontSize = 40.sp, lineHeight = 44.sp, letterSpacing = (-0.01).sp, lineHeightStyle = noTrimHeightStyle)
val headline2     = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Bold,   fontSize = 32.sp, lineHeight = 40.sp, letterSpacing = (-0.01).sp, lineHeightStyle = noTrimHeightStyle)
val headline3     = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Bold,   fontSize = 24.sp, lineHeight = 28.sp, letterSpacing = (-0.01).sp, lineHeightStyle = noTrimHeightStyle)
val headline4     = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Bold,   fontSize = 20.sp, lineHeight = 26.sp, letterSpacing = (-0.01).sp, lineHeightStyle = noTrimHeightStyle)
val subtitle      = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Bold,   fontSize = 16.sp, lineHeight = 20.sp, lineHeightStyle = noTrimHeightStyle)
val body1         = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 20.sp, lineHeight = 30.sp, letterSpacing = (0.005).sp, lineHeightStyle = noTrimHeightStyle)
val body2         = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, lineHeightStyle = noTrimHeightStyle)
val body2Bold     = body2.copy(fontWeight = FontWeight.Bold)
val body1Bold     = body1.copy(fontWeight = FontWeight.Bold)
val caption1      = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 24.sp, lineHeightStyle = noTrimHeightStyle)
val caption1Tight = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 18.sp, lineHeightStyle = noTrimHeightStyle)
val caption1Bold  = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Bold,   fontSize = 14.sp, lineHeight = 18.sp, lineHeightStyle = noTrimHeightStyle)
val caption2      = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 20.sp, lineHeightStyle = noTrimHeightStyle)
val caption2Bold  = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Bold,   fontSize = 12.sp, lineHeight = 20.sp, lineHeightStyle = noTrimHeightStyle)
val overline1     = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Bold,   fontSize = 16.sp, lineHeight = 24.sp, lineHeightStyle = noTrimHeightStyle)
val overline2     = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 18.sp, lineHeightStyle = noTrimHeightStyle)
val button        = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Bold,   fontSize = 16.sp, lineHeight = 16.sp, lineHeightStyle = noTrimHeightStyle)
val bottomTab     = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Bold,   fontSize = 10.sp, lineHeight = 12.sp, lineHeightStyle = noTrimHeightStyle)
val numericXXL    = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 38.sp, lineHeight = 38.sp, lineHeightStyle = noTrimHeightStyle)
val numericXL     = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 32.sp, lineHeight = 32.sp, lineHeightStyle = noTrimHeightStyle)
val numericL      = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 28.sp, lineHeight = 28.sp, lineHeightStyle = noTrimHeightStyle)
val numericM      = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 24.sp, lineHeight = 24.sp, lineHeightStyle = noTrimHeightStyle)
val numericS      = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Bold,   fontSize = 16.sp, lineHeight = 16.sp, lineHeightStyle = noTrimHeightStyle)
val story1        = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 26.sp, lineHeight = 32.sp, letterSpacing = (-0.01).sp, lineHeightStyle = noTrimHeightStyle)
val story1Bold    = story1.copy(fontWeight = FontWeight.Bold, lineHeightStyle = noTrimHeightStyle)
val story2        = TextStyle(fontFamily = robotoFamily, fontWeight = FontWeight.Normal, fontSize = 22.sp, lineHeight = 28.sp, letterSpacing = (-0.01).sp, lineHeightStyle = noTrimHeightStyle)
val story2Bold    = story2.copy(fontWeight = FontWeight.Bold, lineHeightStyle = noTrimHeightStyle)

val spacedBy2  = Arrangement.spacedBy(2.dp)
val spacedBy4  = Arrangement.spacedBy(4.dp)
val spacedBy6  = Arrangement.spacedBy(6.dp)
val spacedBy8  = Arrangement.spacedBy(8.dp)
val spacedBy10 = Arrangement.spacedBy(10.dp)
val spacedBy12 = Arrangement.spacedBy(12.dp)
val spacedBy16 = Arrangement.spacedBy(16.dp)
val spacedBy20 = Arrangement.spacedBy(20.dp)
val spacedBy24 = Arrangement.spacedBy(24.dp)
val spacedBy32 = Arrangement.spacedBy(32.dp)
val spacedBy40 = Arrangement.spacedBy(40.dp)
val spacedBy48 = Arrangement.spacedBy(48.dp)
val spacedBy56 = Arrangement.spacedBy(56.dp)
val spacedBy64 = Arrangement.spacedBy(64.dp)
val spacedBy72 = Arrangement.spacedBy(72.dp)
val spacedBy80 = Arrangement.spacedBy(80.dp)

val rounded = CircleShape
val square  = RoundedCornerShape(0)
val radius1 = RoundedCornerShape(4.dp)
val radius2 = RoundedCornerShape(8.dp)
val radius3 = RoundedCornerShape(12.dp)
val radius4 = RoundedCornerShape(16.dp)
val radius5 = RoundedCornerShape(20.dp)
val radius6 = RoundedCornerShape(24.dp)
val radius7 = RoundedCornerShape(48.dp)

val radius2top    = radius2.copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp))
val radius2bottom = radius2.copy(topEnd    = CornerSize(0.dp), topStart    = CornerSize(0.dp))
val radius4start  = radius4.copy(bottomEnd = CornerSize(0.dp), topEnd      = CornerSize(0.dp))

val buttonRadius1 = RoundedCornerShape(4.dp)
val buttonRadius2 = RoundedCornerShape(8.dp)
val buttonRadius3 = RoundedCornerShape(8.dp)

fun TextStyle.toSpanStyle() = SpanStyle(
    fontFamily = fontFamily,
    fontWeight = fontWeight,
    fontSize   = fontSize)

@Composable
fun AIFinTheme(content: @Composable () -> Unit) {
    darkTheme.value = isSystemInDarkTheme()
    MaterialTheme { content() }
}