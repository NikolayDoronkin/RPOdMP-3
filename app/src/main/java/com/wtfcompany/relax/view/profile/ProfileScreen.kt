package com.wtfcompany.relax.view.profile

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wtfcompany.relax.App
import com.wtfcompany.relax.R
import com.wtfcompany.relax.util.decodeUri
import com.wtfcompany.relax.util.stringToBitmap
import com.wtfcompany.relax.view.profile.ProfileContract.Event.*
import org.koin.androidx.compose.get

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun ProfileScreen(navController: NavHostController) {

    val user = App.instance.user!!
    val viewModel = get<ProfileViewModel>()
    val context = LocalContext.current
    val isLoadingState = remember { mutableStateOf(false) }
    val photos = remember { mutableStateOf(viewModel.photoList) }
    val updateList = remember { mutableStateOf(true) }

    ProfileAction(
        rememberCoroutineScope(),
        context,
        viewModel,
        isLoadingState,
        photos to updateList,
        navController
    ).init()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2D3839))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { viewModel.setEvent(OnMenuButtonClick) }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_menu_24),
                        contentDescription = stringResource(id = R.string.descript_menu),
                        tint = Color.White
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = stringResource(id = R.string.descript_logo),
                    modifier = Modifier.size(80.dp),
                    tint = Color.White
                )
                TextButton(onClick = { viewModel.setEvent(OnLogoutButtonClick) }) {
                    Text(
                        text = stringResource(id = R.string.text_exit),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (user.icon == "") {
                    Image(
                        painterResource(R.drawable.no_photo),
                        contentDescription = stringResource(id = R.string.descript_avatar),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.Gray, CircleShape)
                    )
                } else {
                    stringToBitmap(user.icon)?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = stringResource(id = R.string.descript_avatar),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(130.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.Gray, CircleShape)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(R.string.text_username, user.name),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        stringResource(R.string.text_birthday, user.birthday),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        stringResource(R.string.text_weight, user.weight),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        stringResource(R.string.text_height, user.height),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        stringResource(R.string.text_phone, user.phone),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        stringResource(R.string.text_email, user.email),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            val pickPictureLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.GetContent()
            ) { imageUri ->
                imageUri?.let {
                    val bitmap = decodeUri(context, it, 800)
                    viewModel.setEvent(OnLoadImageButtonClick(bitmap))
                }
            }
            if (updateList.value)
                LazyVerticalGrid(
                    modifier = Modifier.padding(bottom = 50.dp),
                    cells = GridCells.Adaptive(160.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    photos.value.forEach { photo ->
                        item {
                            TextButton(
                                onClick = {
                                    viewModel.setEvent(OnOpenImageButtonClick(photo))
                                },
                                contentPadding = PaddingValues()
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .padding(7.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    backgroundColor = Color.LightGray
                                ) {
                                    stringToBitmap(photo.image)?.let {
                                        Image(
                                            bitmap = it.asImageBitmap(),
                                            contentDescription = stringResource(id = R.string.descript_photo),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                            }
                        }
                    }
                    item {
                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .padding(7.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF7AAD77)),
                            onClick = { pickPictureLauncher.launch("image/*") }
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+",
                                    fontSize = 40.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
        }
        if (isLoadingState.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(60.dp))
            }
        }
    }
}