package com.tusjak.aifin.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.R
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.conditional
import com.tusjak.aifin.common.rememberLaunch
import com.tusjak.aifin.theme.GLOBAL_OPACITY
import com.tusjak.aifin.theme.Palette
import com.tusjak.aifin.theme.body2
import com.tusjak.aifin.theme.button
import com.tusjak.aifin.theme.mainGreen
import com.tusjak.aifin.theme.radius2
import com.tusjak.aifin.theme.radius3
import com.tusjak.aifin.theme.secondaryGreen
import com.tusjak.aifin.theme.solidDanger
import com.tusjak.aifin.theme.square
import com.tusjak.aifin.theme.stateDisabled
import com.tusjak.aifin.theme.surfaceElevation2
import com.tusjak.aifin.theme.surfaceInfo
import com.tusjak.aifin.theme.surfaceSuccess
import com.tusjak.aifin.theme.tintAccent
import com.tusjak.aifin.theme.tintAction
import com.tusjak.aifin.theme.tintOnAccent
import com.tusjak.aifin.theme.tintOnInfo
import com.tusjak.aifin.theme.tintOnSuccess
import com.tusjak.aifin.theme.tintPrimary
import com.tusjak.aifin.theme.transparent
import com.tusjak.aifin.theme.utilityBorderRegularPrimary
import com.tusjak.aifin.theme.value
import kotlinx.coroutines.delay
import ui.common.AfText

data class ButtonStyle(val background: Palette, val content: Palette, val border: Palette, val disabled: Palette)
val buttonContained = ButtonStyle(mainGreen, tintOnAccent, mainGreen, stateDisabled)
val buttonOutlined  = ButtonStyle(secondaryGreen,   tintPrimary,  secondaryGreen,    stateDisabled)
val buttonText      = ButtonStyle(transparent,   tintAccent,   transparent, transparent)
val buttonDanger    = ButtonStyle(transparent,   solidDanger,  utilityBorderRegularPrimary,  stateDisabled)
// small button styles
val buttonInfo         = ButtonStyle(surfaceInfo,       tintOnInfo,   utilityBorderRegularPrimary,  stateDisabled)
val buttonElevation    = ButtonStyle(surfaceElevation2, tintPrimary,  transparent,                  stateDisabled)
val buttonDangerSmall  = ButtonStyle(transparent,       solidDanger,  transparent                ,  stateDisabled)
val buttonSuccessSmall = ButtonStyle(surfaceSuccess,    tintOnSuccess, transparent,                 stateDisabled)

@Composable
fun AfButton(
    text      : String,
    modifier  : Modifier    = M,
    loading   : Boolean     = false,
    enabled   : Boolean     = true,
    startIcon : Int?        = null,
    endIcon   : Int?        = null,
    isFlat    : Boolean     = false,
    isSmall   : Boolean     = false,
    style     : ButtonStyle = buttonContained,
    textStyle : TextStyle   = button,
    iconSize  : Dp          = Dp.Unspecified,
    // height    : Dp?         = null,
    onClick   : () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val background   = if(enabled && !loading) style.background.value else style.disabled.value
    val content      = if(enabled)             style.content.value    else { if(background == Color.Transparent) style.content.value.copy(GLOBAL_OPACITY) else tintPrimary.value.copy(GLOBAL_OPACITY) }
    val border       = if(enabled && !loading) style.border.value     else transparent.value
    val padding      = PaddingValues(horizontal = if(isSmall) 12.dp else 16.dp)

    val radius = when {
        isFlat  -> square
        isSmall -> radius2
        else    -> radius3
    }

    val height = when {
        isFlat  -> 56.dp
        isSmall -> 40.dp
        else    -> 48.dp
    }

    val launch = rememberLaunch()

    CenteredRow(modifier
        .semantics { role = Role.Button }
        .background(background, radius)
        .border(1.dp, border, radius)
        .clip(radius)
        .conditional(!isSmall) { fillMaxWidth() }
        .height(height)
        .clickable(enabled && !loading, onClick = {
            onClick()
            launch { delay(10); focusManager.clearFocus() }
        })
        .padding(padding),
        horizontal = Arrangement.Center,
        content = {
            if (loading)
                //Spinner(isOnAccent = surfaceAccent.value == background)
            else {
                if (startIcon != null)
                    Image(
                        modifier = M
                            .padding(end = 14.dp)
                            .size(iconSize),
                        painter = painterResource(id = startIcon),
                        contentDescription = "",
                    )

                AfText(
                    text     = text,
                    color    = content,
                    maxLines = 1,
                    style    = button)

//                if(endIcon != null)
//                    Icon(
//                        modifier = M.padding(start = 14.dp),
//                        icon     = endIcon,
//                        tint     = content)
            }
        })
}

@Composable
fun AfActionButton(name: String, onClick: () -> Unit) {
    TextButton(
        modifier       = M
            .padding(end = 12.dp)
            .wrapContentHeight(),
        contentPadding = PaddingValues(horizontal = 4.dp, 0.dp),
        onClick        = onClick,
        content        = { Text(name, style = body2.copy(tintAction.value), maxLines = 1, overflow = TextOverflow.Ellipsis) })
}

@Composable
fun AfToolbarTextButton(
    modifier: Modifier = Modifier,
    name: String,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
    style: TextStyle = body2.copy(tintPrimary.value, fontWeight = FontWeight.Bold),
    onClick: () -> Unit
) {
    TextButton(
        modifier       = modifier,
        contentPadding = contentPadding,
        onClick        = onClick,
        content        = { Text(name, style = style) }
    )
}

@Composable
fun SpeedDialFAB(
    onFirstActionClick: () -> Unit,
    onSecondActionClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
        ) {
            AnimatedVisibility(visible = expanded) {
                FloatingActionButton(
                    onClick = {
                        expanded = false
                        onFirstActionClick()
                    },
                    containerColor = mainGreen.value,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        modifier = M.size(24.dp),
                        painter = painterResource(R.drawable.income),
                        contentDescription = "Pridať transakciu"
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                FloatingActionButton(
                    onClick = {
                        expanded = false
                        onSecondActionClick()
                    },
                    containerColor = mainGreen.value,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        modifier = M.size(24.dp),
                        painter = painterResource(R.drawable.expense),
                        contentDescription = "Pridať vydaj"
                    )
                }
            }

            FloatingActionButton(
                onClick = { expanded = !expanded },
                containerColor = mainGreen.value
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = "Toggle actions"
                )
            }
        }
    }
}


@Preview
@Composable
fun Previ1() {
    Column {
        AfButton(text = "Title") { }
        AfButton(text = "Title", loading = true) { }
        AfButton(text = "Title", style = buttonOutlined) { }
        AfButton(text = "Title", style = buttonText) { }
        AfButton(text = "Title", style = buttonDanger) { }
        AfButton(text = "Title", style = buttonInfo) { }
        AfButton(text = "Title", style = buttonElevation) { }
        AfButton(text = "Title", style = buttonDangerSmall) { }

    }
}