package com.wtfcompany.relax.view.firstenter

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wtfcompany.relax.R
import com.wtfcompany.relax.view.firstenter.FirstEnterContract.Event.OnFinishButtonClick
import org.koin.androidx.compose.get
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FirstEnterScreen(navController: NavHostController) {

    val viewModel = get<FirstEnterViewModel>()

    val isLoadingState = remember { mutableStateOf(false) }
    val phoneNumber = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf("") }
    val height = remember { mutableStateOf("") }
    val birthday = remember { mutableStateOf("") }

    FirstEnterAction(
        rememberCoroutineScope(),
        viewModel,
        isLoadingState,
        navController
    ).init()

    val phoneNumberData = ScreenData(
        stringResource(id = R.string.text_enter_phone),
        stringResource(id = R.string.label_phone),
        stringResource(id = R.string.btn_next),
        phoneNumber,
        ::checkPhoneNumber
    )
    val weightData = ScreenData(
        stringResource(id = R.string.text_enter_weight),
        stringResource(id = R.string.label_weight),
        stringResource(id = R.string.btn_next),
        weight,
        ::checkNumber
    )
    val heightData = ScreenData(
        stringResource(id = R.string.text_enter_height),
        stringResource(id = R.string.label_height),
        stringResource(id = R.string.btn_next),
        height,
        ::checkNumber
    )
    val birthdayData = ScreenData(
        stringResource(id = R.string.text_enter_birthday),
        stringResource(id = R.string.label_birthday),
        stringResource(id = R.string.btn_finish),
        birthday,
        ::checkBirthday
    )

    when {
        phoneNumber.value.isEmpty() -> {
            GetDataScreen(screenData = phoneNumberData)
        }
        weight.value.isEmpty() -> {
            GetDataScreen(screenData = weightData)
        }
        height.value.isEmpty() -> {
            GetDataScreen(screenData = heightData)
        }
        phoneNumber.value.isNotEmpty() && weight.value.isNotEmpty() && height.value.isNotEmpty() -> {
            GetDataScreen(screenData = birthdayData, KeyboardType.Text)
        }
    }
    if (phoneNumber.value.isNotEmpty() && weight.value.isNotEmpty() && height.value.isNotEmpty() && birthday.value.isNotEmpty()) {
        viewModel.setEvent(
            OnFinishButtonClick(phoneNumber.value, weight.value, height.value, birthday.value)
        )
    }
}

fun checkBirthday(text: String): Boolean {
    return try {
        val df = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        df.isLenient = false
        df.parse(text)
        true
    } catch (e: ParseException) {
        false
    }
}

fun checkPhoneNumber(text: String): Boolean {
    return text.matches(Regex("[0-9]+"))
}

fun checkNumber(text: String): Boolean {
    val isNumber = text.matches(Regex("[0-9]{1,3}"))
    return if (isNumber) text.toInt() < 200 else false
}

@Composable
private fun GetDataScreen(
    screenData: ScreenData,
    keyboardType: KeyboardType = KeyboardType.Number
) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF283234)),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = screenData.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 10.dp),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .padding(horizontal = 20.dp),
                value = text,
                onValueChange = { text = it },
                label = {
                    Text(
                        screenData.fieldLabel,
                        color = MaterialTheme.colors.primary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .padding(top = 40.dp)
                    .alpha(0.8f),
                onClick = {
                    if (screenData.check(text)) screenData.returnValue.value = text
                    else Toast.makeText(
                        context, context.getString(R.string.toast_check_data), Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = screenData.buttonText,
                    color = Color.White,
                    fontSize = 22.sp
                )
            }
        }
    }
}

private data class ScreenData(
    val title: String,
    val fieldLabel: String,
    val buttonText: String,
    val returnValue: MutableState<String>,
    val check: (String) -> Boolean
)