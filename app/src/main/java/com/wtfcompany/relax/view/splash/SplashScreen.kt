package com.wtfcompany.relax.view.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wtfcompany.relax.App
import com.wtfcompany.relax.R
import com.wtfcompany.relax.data.StoreUserId
import com.wtfcompany.relax.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(navController: NavHostController) {

    val context = LocalContext.current
    var startLogoAnimation by remember { mutableStateOf(false) }
    val alphaLogoAnim = animateFloatAsState(
        targetValue = if (startLogoAnimation) 0f else 1f,
        animationSpec = tween(durationMillis = 1000)
    )
    var startOnboardAnimation by remember { mutableStateOf(false) }
    val alphaOnboardAnim = animateFloatAsState(
        targetValue = if (startOnboardAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(key1 = true) {
        delay(1000)
        val userId = StoreUserId(context).getUserId.first()
        if (userId != null && userId != -1L) {
            val user = App.instance.userRepository.getUser(userId)[0]
            App.instance.user = user
            if (user.phone.isNotEmpty()) {
                navController.popBackStack()
                navController.navigate(Screen.Main.route)
            } else {
                navController.popBackStack()
                navController.navigate(Screen.FirstEnter.route)
            }
        } else {
            delay(1000)
            startLogoAnimation = true
            delay(500)
            startOnboardAnimation = true
        }
    }

    Splash(alpha = alphaLogoAnim.value)
    Onboarding(
        alpha = alphaOnboardAnim.value,
        onClickLogin = {
            navController.popBackStack()
            navController.navigate(Screen.Login.route)
        },
        onClickRegister = {
            navController.popBackStack()
            navController.navigate(Screen.Registration.route)
        }
    )
}

@Composable
private fun Splash(alpha: Float) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = stringResource(id = R.string.descript_background),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )

        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = stringResource(id = R.string.descript_logo),
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.None,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = (alpha * 200).dp)
        )
    }
}

@Composable
private fun Onboarding(
    alpha: Float,
    onClickRegister: () -> Unit,
    onClickLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha = alpha),
        verticalArrangement = Arrangement.Bottom
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp)
        ) {
            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                onClick = onClickLogin
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = stringResource(id = R.string.text_sign_in),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.text_not_account),
                    color = Color.White,
                    fontSize = 16.sp
                )
                TextButton(onClick = onClickRegister) {
                    Text(
                        text = stringResource(id = R.string.btn_register),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                }
            }
        }
    }
}