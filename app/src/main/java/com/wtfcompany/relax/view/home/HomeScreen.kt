package com.wtfcompany.relax.view.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wtfcompany.relax.App
import com.wtfcompany.relax.R
import com.wtfcompany.relax.util.stringToBitmap
import com.wtfcompany.relax.view.home.HomeContract.Event.OnMenuButtonClick
import org.koin.androidx.compose.get

@Composable
fun HomeScreen(navController: NavHostController) {

    val user = App.instance.user!!
    val viewModel = get<HomeViewModel>()
    val updateList = remember { mutableStateOf(true) }
    val updateMood = remember { mutableStateOf(true) }
    val recommendationData = remember {
        mutableStateOf(Triple(viewModel.horoscopeData, viewModel.todayMood, viewModel.dailyMood))
    }
    val moodList = remember { mutableStateOf(viewModel.moodData) }

    HomeAction(
        rememberCoroutineScope(),
        viewModel,
        updateList,
        updateMood,
        recommendationData,
        moodList,
        navController
    ).init()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D3839))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
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
            if (user.icon == "") {
                Image(
                    painterResource(R.drawable.no_photo),
                    contentDescription = stringResource(id = R.string.descript_avatar),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(30.dp)
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
                            .size(30.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.Gray, CircleShape)
                    )
                }
            }
        }
        Text(
            text = stringResource(id = R.string.text_welcome_back, user.name),
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        )
        Text(
            text = stringResource(id = R.string.text_feel),
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        )

        if (updateMood.value) {
            LazyRow(
                modifier = Modifier.padding(vertical = 5.dp)
            ) {
                moodList.value.forEach {
                    item {
                        MoodItem(moodInfo = it, viewModel = viewModel)
                    }
                }
            }
        }

        val (horoscopeData, todayMood, dailyMood) = recommendationData.value
        val suggestionData = mutableListOf<SuggestionInfo>()
        if (horoscopeData != null)
            suggestionData.add(
                SuggestionInfo(
                    stringResource(id = R.string.text_horoscope),
                    stringResource(id = R.string.text_zodiac),
                    horoscopeData.description,
                    R.drawable.horoscope
                )
            )
        suggestionData.add(
            SuggestionInfo(
                stringResource(id = R.string.text_cur_recommendation),
                stringResource(id = R.string.text_today_recommendation),
                stringResource(id = todayMood.nameRes),
                R.drawable.current_recommendation
            )
        )
        suggestionData.add(
            SuggestionInfo(
                stringResource(id = R.string.text_daily_recommendation),
                stringResource(id = R.string.text_average_recommendation),
                stringResource(id = dailyMood.nameRes),
                R.drawable.daily_recommendation
            )
        )
        if (updateList.value)
            LazyColumn(modifier = Modifier.padding(bottom = 40.dp)) {
                suggestionData.forEach {
                    item { ExpandableItem(it) }
                }
            }
    }
}