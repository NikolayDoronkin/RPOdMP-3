package com.wtfcompany.relax.view.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.get

import com.wtfcompany.relax.R


@Composable
fun MenuScreen(navController: NavHostController) {

    val viewModel = get<MenuViewModel>()

    MenuAction(
        rememberCoroutineScope(), viewModel, navController
    ).init()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D3839))
            .padding(16.dp)
    ) {
        Text(
            stringResource(id = R.string.text_menu),
            fontSize = 30.sp,
            color = Color.White,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        TextButton(
            onClick = { viewModel.setEvent(MenuContract.Event.OnEditProfileButtonClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = stringResource(id = R.string.descript_edit),
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(
                stringResource(id = R.string.menu_edit_profile),
                fontSize = 18.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        TextButton(
            onClick = { viewModel.setEvent(MenuContract.Event.OnCalculateBmiButtonClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_weight),
                contentDescription = stringResource(id = R.string.descript_bmi),
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(
                stringResource(id = R.string.menu_bmi),
                fontSize = 18.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        TextButton(
            onClick = { viewModel.setEvent(MenuContract.Event.OnAboutDeveloperButtonClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_info),
                contentDescription = stringResource(id = R.string.descript_about),
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(
                stringResource(id = R.string.menu_about_developer),
                fontSize = 18.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        TextButton(
            onClick = { viewModel.setEvent(MenuContract.Event.OnGuideButtonClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_guide),
                contentDescription = stringResource(id = R.string.descript_guide),
                modifier = Modifier.padding(end = 10.dp)
            )
            Text(
                stringResource(id = R.string.menu_guide),
                fontSize = 18.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}