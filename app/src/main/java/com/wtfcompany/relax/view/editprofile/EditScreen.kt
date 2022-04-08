package com.wtfcompany.relax.view.editprofile

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wtfcompany.relax.App
import com.wtfcompany.relax.R
import com.wtfcompany.relax.util.bitmapToString
import com.wtfcompany.relax.util.decodeUri
import com.wtfcompany.relax.util.stringToBitmap
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditScreen(navController: NavHostController) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val user = App.instance.user!!

    var name by remember { mutableStateOf(user.name) }
    var phoneNumber by remember { mutableStateOf(user.phone) }
    var weight by remember { mutableStateOf(user.weight) }
    var height by remember { mutableStateOf(user.height) }
    var birthday by remember { mutableStateOf(user.birthday) }
    var loading by remember { mutableStateOf(false) }
    var updateIcon by remember { mutableStateOf(true) }
    var icon by remember { mutableStateOf(user.icon) }

    val pickPictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { imageUri ->
        imageUri?.let {
            updateIcon = false
            val btm = decodeUri(context, it, 500)
            icon = bitmapToString(btm!!)
            updateIcon = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D3839))
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(id = R.string.text_update_profile),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                if (updateIcon) {
                    if (icon == "") {
                        Image(
                            painterResource(R.drawable.no_photo),
                            contentDescription = stringResource(id = R.string.descript_avatar),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.Gray, CircleShape)
                                .clickable {
                                    pickPictureLauncher.launch("image/*")
                                }
                        )
                    } else {
                        stringToBitmap(icon)?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = stringResource(id = R.string.descript_avatar),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.Gray, CircleShape)
                                    .clickable {
                                        pickPictureLauncher.launch("image/*")
                                    }
                            )
                        }
                    }
                }
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
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
                    .padding(top = 14.dp)
                    .padding(horizontal = 20.dp),
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = {
                    Text(
                        stringResource(id = R.string.label_phone),
                        color = MaterialTheme.colors.primary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp)
                    .padding(horizontal = 20.dp),
                value = weight,
                onValueChange = { weight = it },
                label = {
                    Text(
                        stringResource(id = R.string.label_weight),
                        color = MaterialTheme.colors.primary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp)
                    .padding(horizontal = 20.dp),
                value = height,
                onValueChange = { height = it },
                label = {
                    Text(
                        stringResource(id = R.string.label_height),
                        color = MaterialTheme.colors.primary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp)
                    .padding(horizontal = 20.dp),
                value = birthday,
                onValueChange = { birthday = it },
                label = {
                    Text(
                        stringResource(id = R.string.label_birthday),
                        color = MaterialTheme.colors.primary
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.White,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    loading = true
                    if (checkNumber(phoneNumber) && check3Number(weight)
                        && check3Number(height) && checkBirthday(birthday)
                    ) {
                        coroutineScope.launch {
                            user.name = name
                            user.phone = phoneNumber
                            user.weight = weight
                            user.height = height
                            user.birthday = birthday
                            user.icon = icon
                            App.instance.userRepository.update(user)
                            navController.popBackStack()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_check_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    loading = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 30.dp)
            ) {
                Text(stringResource(id = R.string.btn_update))
            }
        }
        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }
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

fun checkNumber(text: String): Boolean {
    return text.matches(Regex("[0-9]+"))
}

fun check3Number(text: String): Boolean {
    return text.matches(Regex("[0-9]{1,3}"))
}