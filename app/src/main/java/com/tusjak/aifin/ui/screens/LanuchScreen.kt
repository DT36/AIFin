package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.R
import com.tusjak.aifin.common.M
import com.tusjak.aifin.theme.AIFinTheme
import com.tusjak.aifin.theme.background
import com.tusjak.aifin.theme.body1
import com.tusjak.aifin.theme.headline1
import com.tusjak.aifin.theme.mainGreen
import com.tusjak.aifin.theme.switchTheme
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.ui.common.AfButton
import com.tusjak.aifin.ui.common.buttonOutlined

@Composable
fun LaunchScreen(onLoginButtonClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(background.value),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = M.padding(bottom = 8.dp),
            painter = painterResource(id = R.drawable.logo_graph),
            contentDescription = "",
            colorFilter = ColorFilter.tint(mainGreen.value),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = M.padding(bottom = 8.dp), text = stringResource(R.string.app_name_long), style = headline1,
            color = mainGreen.value
        )

        Text(
            modifier = M.padding(horizontal = 32.dp, vertical = 8.dp),
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod. ",
            textAlign = TextAlign.Center,
            style = body1,
            color = textColor.value
        )

        AfButton(modifier = M.padding(horizontal = 48.dp, vertical = 16.dp), text = stringResource(R.string.login)) {
            onLoginButtonClicked()
        }

        AfButton(modifier = M.padding(horizontal = 48.dp,), text = stringResource(R.string.sign_up), style = buttonOutlined) {
            onLoginButtonClicked()
        }
    }
}

@Preview
@Composable
fun LaunchScreenPreview() {
    AIFinTheme {
        LaunchScreen(onLoginButtonClicked = {})
    }
}
@Preview
@Composable
fun LaunchScreenDarkPreview() {
    AIFinTheme {
        LaunchScreen(onLoginButtonClicked = {})
        switchTheme()
    }
}
