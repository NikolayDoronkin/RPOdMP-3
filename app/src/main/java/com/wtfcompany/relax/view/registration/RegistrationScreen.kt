package com.wtfcompany.relax.view.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wtfcompany.relax.R
import com.wtfcompany.relax.view.registration.RegistrationContract.Event.OnAuthButtonClick
import com.wtfcompany.relax.view.registration.RegistrationContract.Event.OnLoginButtonClick
import org.koin.androidx.compose.get

@Composable
fun RegistrationScreen(navController: NavHostController) {

    val viewModel = get<RegistrationViewModel>()
    val context = LocalContext.current
    val isLoadingState = remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    RegistrationAction(
        rememberCoroutineScope(), context, viewModel, isLoadingState, navController
    ).init()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF283234))
    ) {
        Image(
            painter = painterResource(R.drawable.leaf),
            contentDescription = stringResource(id = R.string.descript_leaf),
            alignment = Alignment.BottomEnd,
            contentScale = ContentScale.None,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .padding(bottom = 10.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = stringResource(id = R.string.descript_logo),
                alignment = Alignment.BottomEnd,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(100.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(id = R.string.text_sign_up),
                color = Color.White,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                value = name,
                onValueChange = { name = it },
                label = {
                    Text(
                        stringResource(id = R.string.label_name),
                        color = MaterialTheme.colors.primary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White
                )
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(horizontal = 20.dp),
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        stringResource(id = R.string.label_email),
                        color = MaterialTheme.colors.primary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(horizontal = 20.dp),
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        stringResource(id = R.string.label_password),
                        color = MaterialTheme.colors.primary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 30.dp)
                    .alpha(0.8f),
                onClick = {
                    viewModel.setEvent(OnAuthButtonClick(name, email, password))
                }
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = stringResource(id = R.string.text_sign_up),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.text_is_account),
                    color = Color.White,
                    fontSize = 16.sp
                )
                TextButton(
                    onClick = {
                        viewModel.setEvent(OnLoginButtonClick)
                    }
                )
                {
                    Text(
                        text = stringResource(id = R.string.text_sign_in),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                }
            }
        }
        if (isLoadingState.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }
    }
}