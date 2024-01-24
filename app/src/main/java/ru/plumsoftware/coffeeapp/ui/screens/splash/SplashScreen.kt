package ru.plumsoftware.coffeeapp.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.plumsoftware.coffeeapp.R
import ru.plumsoftware.coffeeapp.ui.theme.Padding
import ru.plumsoftware.coffeeapp.ui.theme.getRegularWeight
import ru.plumsoftware.coffeeapp.ui.theme.getSplashScreenTitleFont

@Composable
fun SplashScreen(splashScreenViewModel: SplashScreenViewModel) {

    splashScreenViewModel.onOutput(output = SplashScreenViewModel.Output.GetUser(isFirst = splashScreenViewModel.getIsFirst()))


    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(textDecoration = TextDecoration.None)
        ) {
            append(stringResource(id = R.string.splash_screen_title_1))
        }
        append(" ")
        withStyle(
            style = SpanStyle(textDecoration = TextDecoration.Underline)
        ) {
            append(stringResource(id = R.string.splash_screen_title_2))
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = Padding.Items.splashScreenPadding,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(Padding.Screens.smallScreenPadding)
    ) {

        Text(
            modifier = Modifier.wrapContentSize(),
            text = annotatedText, style = TextStyle(
                fontSize = 32.sp,
                fontFamily = getSplashScreenTitleFont(),
                fontWeight = getRegularWeight(),
                textAlign = TextAlign.Center,
            )
        )

        Image(
            painter = painterResource(id = R.drawable.splash_logo_1),
            contentDescription = stringResource(
                id = R.string.splash_screen_logo_cd
            ),
            modifier = Modifier
                .width(479.dp)
                .height(479.dp),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SplashScreenPreview() {
    SplashScreen(
        splashScreenViewModel = SplashScreenViewModel(
            sharedPreferencesStorage = null,
            output = {})
    )
}