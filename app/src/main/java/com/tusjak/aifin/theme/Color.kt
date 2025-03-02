package com.tusjak.aifin.theme


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

const val GLOBAL_OPACITY      = 0.5f
const val SKELETON_ALPHA      = 0.25f

val darkTheme = mutableStateOf(true)
fun switchTheme() { darkTheme.value = !darkTheme.value }

val Palette.value get() = if(darkTheme.value) dark else light
val PaletteGradient.value get() = if(darkTheme.value) dark else light

fun Palette.useOpacity(useOpacity: Boolean, opacity: Float = GLOBAL_OPACITY) =
    if (useOpacity) copy(light = light.copy(alpha = light.alpha * opacity), dark = dark.copy(alpha = dark.alpha * opacity)) else this

data class Palette(val light: Color, val dark: Color)
data class PaletteGradient(val light: Brush, val dark: Brush)

fun Palette.withDisabledOpacity() = value.copy(value.alpha * GLOBAL_OPACITY)
fun Palette.withSkeletonOpacity() = value.copy(value.alpha * SKELETON_ALPHA)
fun Color.withDisabledOpacity() = copy( alpha * GLOBAL_OPACITY)

fun Palette.withDisabledOpacity(enabled: Boolean) = if(enabled) value else value.copy(value.alpha * GLOBAL_OPACITY)
fun Color.withDisabledOpacity(enabled: Boolean) = if(enabled) this else copy( alpha * GLOBAL_OPACITY)

val transparent = Palette(Color.Transparent, Color.Transparent)
val transparentGradient = PaletteGradient(Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent)), Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent)))

val surfaceInverted      = Palette(Color(0xFF100F15), Color(0xFFFFFFFF))
val surfaceBackground    = Palette(Color(0xFFF4F4F5), Color(0xFF100F15))
val surfaceBaseline      = Palette(Color(0xFFFFFFFF), Color(0xFF1F1E2A))
val surfaceForeground    = Palette(Color(0xFFF4F4F5), Color(0xFF2A2D3B))
val surfaceModal         = Palette(Color(0xFFFFFFFF), Color(0xFF2A2D3B))
val surfaceSticky        = Palette(Color(0xFFFFFFFF), Color(0xFF2A2D3B))
val surfaceAccent        = Palette(Color(0xFF0097FD), Color(0xFF0097FD))
val surfaceSplash        = Palette(Color(0xFF100F15), Color(0xFF100F15))
val surfaceInfo          = Palette(Color(0xFFAAECF5), Color(0xFF085964))
val surfaceSuccess       = Palette(Color(0xFFAEF2C1), Color(0xFF0D6024))
val surfaceWarning       = Palette(Color(0xFFFEE8B7), Color(0xFF6E5519))
val surfaceDanger        = Palette(Color(0xFFFEBCC0), Color(0xFF6E1F24))
val surfaceSelection     = Palette(Color(0xFFA3DAFE), Color(0xFF00426F))
val surfaceCellSelection = Palette(Color(0xFFFFFFFF), Color(0xFF1F1E2A))
val surfaceElevation     = Palette(Color(0x1f8589A3), Color(0x1f8589A3))
val surfaceElevation2    = Palette(Color(0x4d8589A3), Color(0x668589A3))
val surfaceBackdrop      = Palette(Color(0x80100F15), Color(0xb3100F15))
val surfaceLight         = Palette(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
val surfaceTranslucent   = Palette(Color(0x00FFFFFF), Color(0x00100F15))

//vertical
val surfaceTransparentToBaseline = PaletteGradient(
    Brush.verticalGradient(0.0f to Color(0x00ffffff), 0.7f to Color(0xccffffff), 1.0f to Color(0xffffffff)),
    Brush.verticalGradient(0.0f to Color(0x001F1E2A), 0.7f to Color(0xcc1F1E2A), 1.0f to Color(0xff1F1E2A)))

// horizontal
val surfaceTransparentToBaselineH = PaletteGradient(
    Brush.horizontalGradient(0.0f to Color(0x00ffffff), 0.7f to Color(0xccffffff), 1.0f to Color(0xffffffff)),
    Brush.horizontalGradient(0.0f to Color(0x001F1E2A), 0.7f to Color(0xcc1F1E2A), 1.0f to Color(0xff1F1E2A)))

val scannerBorder = PaletteGradient(
    Brush.linearGradient(listOf(Color(0xffC339EC), Color(0xff5D66E3), Color(0xff5D66E3), Color(0xff5FC6E5), Color(0xffA2E86E), Color(0xffDBD14E), Color(0xffCE5E96))),
    Brush.linearGradient(listOf(Color(0xffC339EC), Color(0xff5D66E3), Color(0xff5D66E3), Color(0xff5FC6E5), Color(0xffA2E86E), Color(0xffDBD14E), Color(0xffCE5E96)))
)

//val activeLevel = PaletteGradient(
//  Brush.linearGradient(colors = listOf(Color(0xFF3C00E7), Color(0xFFFD00E4)), start = Offset(0f, Float.POSITIVE_INFINITY), end = Offset(Float.POSITIVE_INFINITY, 0f)),
//  Brush.linearGradient(colors = listOf(Color(0xFF3C00E7), Color(0xFFFD00E4)), start = Offset(0f, Float.POSITIVE_INFINITY), end = Offset(Float.POSITIVE_INFINITY, 0f))
//)

@Composable
fun cardBoxBackground() = PaletteGradient(
    Brush.radialGradient(colors = listOf(Color(0xFF117C83), Color(0xFF54189E), Color(0xFF54189E), Color(0xFF8A1DC8),), center = Offset(with(LocalDensity.current) { 300.dp.toPx() }, with(LocalDensity.current) { -20.dp.toPx() }), radius = with(LocalDensity.current) { 700.dp.toPx() }),
    Brush.radialGradient(colors = listOf(Color(0xFF117C83), Color(0xFF54189E), Color(0xFF54189E), Color(0xFF8A1DC8),), center = Offset(with(LocalDensity.current) { 300.dp.toPx() }, with(LocalDensity.current) { -20.dp.toPx() }), radius = with(LocalDensity.current) { 700.dp.toPx() }),
)

val surfaceTransparentToBackground = PaletteGradient(
    Brush.verticalGradient(0.0f to Color(0x00f4f4f5), 0.7f to Color(0xccf4f4f5), 1.0f to Color(0xfff4f4f5)),
    Brush.verticalGradient(0.0f to Color(0x00100F15), 0.7f to Color(0xcc100F15), 1.0f to Color(0xff100F15)))

val surfaceTransparentToBackgroundInverted = PaletteGradient(
    Brush.verticalGradient(0.0f to Color(0xE6f4f4f5), 0.5f to Color(0xB3f4f4f5), 1.0f to Color(0x4Df4f4f5)),
    Brush.verticalGradient(0.0f to Color(0xE6100F15), 0.5f to Color(0xB3100F15), 1.0f to Color(0x4D100F15)))


val tintPrimary             = Palette(Color(0xFF1F1E2A), Color(0xFFFFFFFF))
val tintSecondary           = Palette(Color(0xFF656A85), Color(0xFF9FA1B2))
val tintTertiary            = Palette(Color(0xFF8589A3), Color(0xFF8589A3))
val tintAccent              = Palette(Color(0xFF0097FD), Color(0xFF0097FD))
val tintAction              = Palette(Color(0xFF0097FD), Color(0xFF0097FD))
val tintOnInfo              = Palette(Color(0xFF085964), Color(0xFF77E1EF))
val tintOnSuccess           = Palette(Color(0xFF0D6024), Color(0xFFAEF2C1))
val tintOnSelection         = Palette(Color(0xFF00426F), Color(0xFFA3DAFE))
val tintOnWarning           = Palette(Color(0xFF6E5519), Color(0xFFFDDB8D))
val tintOnDanger            = Palette(Color(0xFF6E1F24), Color(0xFFFD959B))
val tintOnAccent            = Palette(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
val tintOnSolid             = Palette(Color(0xFF1F1E2A), Color(0xFF1F1E2A))
val tintOnSolidAccentInvert = Palette(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
val tintPrimaryOnInverted   = Palette(Color(0xFFFFFFFF), Color(0xFF100F15))
val tintActionOnInverted    = Palette(Color(0xFF0097FD), Color(0xFF0097FD))

val solidInfo         = Palette(Color(0xFF0FA4B9), Color(0xFF12CAE3))
val solidSuccess      = Palette(Color(0xFF18B243), Color(0xFF1DDB52))
val solidWarning      = Palette(Color(0xFFCC9C2E), Color(0xFFFBC038))
val solidDanger       = Palette(Color(0xFFCC3942), Color(0xFFFB4651))
val solidSelection    = Palette(Color(0xFF36ADFD), Color(0xFF0097FD))
val solidAccentInvert = Palette(Color(0xFF1F1E2A), Color(0xFF1F1E2A))

val stateDisabled         = Palette(Color(0x3d8589A3), Color(0x3d8589A3))
val statePressed          = Palette(Color(0x1a8589A3), Color(0x1a8589A3))
val stateButtonHover      = Palette(Color(0x1f100F15), Color(0x1fffffff))
val stateButtonPressed    = Palette(Color(0x40100F15), Color(0x40ffffff))
val statePressedAccent    = Palette(Color(0xFF007BCE), Color(0xFF36ADFD))
val statePressedDanger    = Palette(Color(0xFF9D2C33), Color(0xFFFC6E76))
val hairlineRegularBottom = Palette(Color(0x3D858EA3), Color(0x3D858EA3))
val hairlineRegularTop    = Palette(Color(0x3D858EA3), Color(0x3D858EA3))

val utilityStatePressed               = Palette(Color(0x1A8589A3), Color(0x1A8589A3))

val utilityBorderOutlineButton        = Palette(Color(0xFF0097FD), Color(0xFF0097FD))
val utilityBorderOutlineButtonRegular = Palette(Color(0x4d8589A3), Color(0x4d8589A3))
val utilityBorderRegularAccent        = Palette(Color(0xFF0097FD), Color(0xFF0097FD))
val utilityBorderRegularPrimary       = Palette(Color(0x4d9FA1B2), Color(0x4d656A85))
val utilityBorderEmphasisPrimary      = Palette(Color(0x998589A3), Color(0x998589A3))
val utilityBorderCellSelection        = Palette(Color(0xFF0097FD), Color(0xFF0097FD))
val utilityBorderGradientPrimary      = Palette(Color(0x1a100F15), Color(0x14FFFFFF))
val utilityBorderRegularInverted      = Palette(Color(0x4d858ea3), Color(0xb308080a))

val chartAqua    = Palette(Color(0xFF74EEB6), Color(0xFF29E58E))
val chartBlue    = Palette(Color(0xFF48A0EE), Color(0xFF0893E4))
val chartPurple  = Palette(Color(0xFFC954F4), Color(0xFFC11AFC))
val chartMagenta = Palette(Color(0xFFF836AA), Color(0xFFF40093))
val chartOrange  = Palette(Color(0xFFF59243), Color(0xFFED6F2E))
val chartRed     = Palette(Color(0xFFF0555F), Color(0xFFFB3947))
val chartBrown   = Palette(Color(0xFFBF742D), Color(0xFFA44F00))
val chartYellow  = Palette(Color(0xFFF6CA35), Color(0xFFFFDC18))
val chartNone    = Palette(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
val chartGreen   = Palette(Color(0xFFB1EC59), Color(0xFF87E100))
val chartCyan    = Palette(Color(0xFF66ECEC), Color(0xFF14E2E2))
val chartGrey    = Palette(Color(0xFF88A8B7), Color(0xFF5D96B1))

val surfaceMapChapterBackgroundGradient = PaletteGradient(
    Brush.verticalGradient(listOf(Color(0x1F1A187E), Color(0x1F8F9EC3))),
    Brush.verticalGradient(listOf(Color(0x1F1A187E), Color(0x1F8F9EC3))))

val tintActiveLevelBlue       = Palette(Color(0xFF3C00E7), Color(0xFF3C00E7))
val tintActiveLevelMagenta    = Palette(Color(0xFFFD00E4), Color(0xFFFD00E4))
val tintDefaultLevelPrimary   = Palette(Color(0xFF1E1E1E), Color(0xFF1E1E1E))
val tintDefaultLevelSecondary = Palette(Color(0xFF4F4F4F), Color(0xFF4F4F4F))

const val defaultMovementColor = "5336439"

val personaPreview = Palette(Color(0xFF1927A4), Color(0xFF1927A4))
val personaSuccess = Palette(Color(0xFF0D6B2D), Color(0xFF0D6B2D))
val personaFailure = Palette(Color(0xFF640C1C), Color(0xFF640C1C))

val defaultLevelGradient = Brush.horizontalGradient(listOf(Color(0xFF1E1E1E), Color(0xFF4F4F4F)))

val leagueFirstGradient    = Brush.horizontalGradient(listOf(Color(0xFFCDA413).copy(alpha = 0.5f), Color(0xFFCDA413).copy(alpha = 0f)))
val leagueSecondGradient   = Brush.horizontalGradient(listOf(Color(0xFF88A1B8).copy(alpha = 0.5f), Color(0xFF88A1B8).copy(alpha = 0f)))
val leagueThirdGradient    = Brush.horizontalGradient(listOf(Color(0xFFC07319).copy(alpha = 0.5f), Color(0xFFC07319).copy(alpha = 0f)))
val leaguePositionGradient = Brush.horizontalGradient(listOf(Color(0xFF2D18AE).copy(alpha = 0.5f), Color(0xFF2D18AE).copy(alpha = 0f)))

val charadesOverlay             = Palette(Color(0xFF100F15), Color(0xFF100F15))
val charadesRandomGradientRight = Brush.horizontalGradient(listOf(Color(0xFF100F15).copy(alpha = 1.0f), Color(0xFF100F15).copy(alpha = 0f)))
val charadesRandomGradientLeft  = Brush.horizontalGradient(listOf(Color(0xFF100F15).copy(alpha = 0f), Color(0xFF100F15).copy(alpha = 1.0f)))

val yellow = Palette(Color(0xFFFFDC18), Color(0xFFF6CA35))
val caribbeanGreen = Palette(Color(0xFF00D09E), Color(0xFF00D09E))

val honeydew = Color(0xFFF1FFF3)
val lightGreen = Color(0xFFDFF7E2)
val caribbeanGreenColor = Color(0xFF00D09E)
val cyprus = Color(0xFF0E3E3E)
val fenceGreen = Color(0xFF052224)
val void = Color(0xFF031314)
val lightBlue = Color(0xFF6DB6FE)
val vividBlue = Color(0xFF3299FF)
val OceanBlue = Color(0xFF0068FF)

val background = Palette(Color(0xFFF1FFF3), Color(0xFF052224))
val mainGreen = Palette(Color(0xFF00D09E), Color(0xFF00D09E))
val secondaryGreen = Palette(Color(0xFFDFF7E2), Color(0xFFDFF7E2))
val textColor = Palette(Color(0xFF1F1E2A), Color(0xFFDFF7E2))
