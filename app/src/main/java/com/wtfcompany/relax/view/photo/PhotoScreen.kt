package com.wtfcompany.relax.view.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wtfcompany.relax.data.entity.Photo
import com.wtfcompany.relax.util.stringToBitmap
import com.wtfcompany.relax.R
import com.wtfcompany.relax.view.photo.PhotoContract.Event
import org.koin.androidx.compose.get

@Composable
fun PhotoScreen(navController: NavHostController, photo: Photo) {

    val viewModel = get<PhotoViewModel>()

    PhotoAction(
        rememberCoroutineScope(), viewModel, navController
    ).init()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D3839))
    ) {
        stringToBitmap(photo.image)?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = stringResource(id = R.string.descript_photo),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 100.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(onClick = { viewModel.setEvent(Event.OnDeleteImageButtonClick(photo)) }) {
                Text(
                    text = stringResource(id = R.string.btn_delete),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            TextButton(onClick = { viewModel.setEvent(Event.OnCloseImageButtonClick) }) {
                Text(
                    text = stringResource(id = R.string.btn_close),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}