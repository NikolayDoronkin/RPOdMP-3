package com.wtfcompany.relax.view.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wtfcompany.relax.R

@Composable
fun AboutScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D3839))
            .padding(20.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 25.dp),
            text = stringResource(id = R.string.text_about),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White
        )
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp),
            text = stringResource(id = R.string.lab_developer),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp),
            text = stringResource(id = R.string.lab_name),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp),
            text = stringResource(id = R.string.lab_groupe),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp),
            text = stringResource(id = R.string.lab_number),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}