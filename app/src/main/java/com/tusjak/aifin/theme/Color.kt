package com.tusjak.aifin.theme


import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

const val GLOBAL_OPACITY      = 0.90f

val darkTheme = mutableStateOf(true)
fun switchTheme() { darkTheme.value = !darkTheme.value }

val Palette.value get() = if(darkTheme.value) dark else light
val PaletteGradient.value get() = if(darkTheme.value) dark else light

fun Palette.useOpacity(useOpacity: Boolean, opacity: Float = GLOBAL_OPACITY) =
    if (useOpacity) copy(light = light.copy(alpha = light.alpha * opacity), dark = dark.copy(alpha = dark.alpha * opacity)) else this

data class Palette(val light: Color, val dark: Color)
data class PaletteGradient(val light: Brush, val dark: Brush)


fun Color.withDisabledOpacity() = copy( alpha * GLOBAL_OPACITY)

fun Palette.withDisabledOpacity(enabled: Boolean) = if(enabled) value else value.copy(value.alpha * GLOBAL_OPACITY)
fun Color.withDisabledOpacity(enabled: Boolean) = if(enabled) this else copy( alpha * GLOBAL_OPACITY)

val transparent = Palette(Color.Transparent, Color.Transparent)
val transparentGradient = PaletteGradient(Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent)), Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent)))

val surfaceInverted      = Palette(Color(0xFF100F15), Color(0xFFFFFFFF))
val surfaceInfo          = Palette(Color(0xFFAAECF5), Color(0xFF085964))
val surfaceSuccess       = Palette(Color(0xFFAEF2C1), Color(0xFF0D6024))
val surfaceElevation2    = Palette(Color(0x4d8589A3), Color(0x668589A3))



val tintPrimary             = Palette(Color(0xFF1F1E2A), Color(0xFFFFFFFF))

val tintAccent              = Palette(Color(0xFF0097FD), Color(0xFF0097FD))
val tintAction              = Palette(Color(0xFF0097FD), Color(0xFF0097FD))
val tintOnInfo              = Palette(Color(0xFF085964), Color(0xFF77E1EF))
val tintOnSuccess           = Palette(Color(0xFF0D6024), Color(0xFFAEF2C1))
val tintOnAccent            = Palette(Color(0xFFFFFFFF), Color(0xFFFFFFFF))

val solidDanger       = Palette(Color(0xFFCC3942), Color(0xFFFB4651))


val stateDisabled         = Palette(Color(0x3d8589A3), Color(0x3d8589A3))
val statePressed          = Palette(Color(0x1a8589A3), Color(0x1a8589A3))
val stateButtonHover      = Palette(Color(0x1f100F15), Color(0x1fffffff))
val stateButtonPressed    = Palette(Color(0x40100F15), Color(0x40ffffff))
val statePressedAccent    = Palette(Color(0xFF007BCE), Color(0xFF36ADFD))
val statePressedDanger    = Palette(Color(0xFF9D2C33), Color(0xFFFC6E76))
val hairlineRegularBottom = Palette(Color(0x3D858EA3), Color(0x3D858EA3))
val hairlineRegularTop    = Palette(Color(0x3D858EA3), Color(0x3D858EA3))


val utilityBorderRegularPrimary = Palette(Color(0x4d9FA1B2), Color(0x4d656A85))

val yellow = Palette(Color(0xFFFFDC18), Color(0xFFF6CA35))


val honeydew       = Color(0xFFF1FFF3)
val lightGreen     = Color(0xFFDFF7E2)
val caribbeanGreen = Color(0xFF00D09E)
val cyprus         = Color(0xFF0E3E3E)
val fenceGreen     = Color(0xFF052224)
val void           = Color(0xFF031314)
val lightBlue      = Color(0xFF6DB6FE)
val vividBlue      = Color(0xFF3299FF)
val oceanBlue      = Color(0xFF0068FF)

val background      = Palette(Color(0xFFF1FFF3), Color(0xFF0E3E3E))
val mainGreen       = Palette(Color(0xFF00D09E), Color(0xFF052224))
val greenBar        = Palette(Color(0xFFDFF7E2), Color(0xFF052224))
val greenIcon       = Palette(Color(0xFFDFF7E2), Color(0xFF00D09E))
val caribbeanGreenP = Palette(Color(0xFF00D09E), Color(0xFF00D09E))
val secondaryGreen  = Palette(Color(0xFFDFF7E2), Color(0xFFDFF7E2))
val textColor       = Palette(Color(0xFF1F1E2A), Color(0xFFDFF7E2))