package com.wtfcompany.relax.view.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wtfcompany.relax.R

@Composable
fun MoodItem(moodInfo: MoodInfo, viewModel: HomeViewModel) {
    TextButton(
        modifier = Modifier.padding(horizontal = 5.dp),
        onClick = {
            viewModel.setEvent(HomeContract.Event.OnMoodButtonClick(moodInfo.mood))
        },
        contentPadding = PaddingValues()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                modifier = Modifier
                    .size(80.dp)
                    .padding(6.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.White,
                border = if (moodInfo.isSelected) BorderStroke(3.dp, Color(0xFFFF5F5F)) else null
            ) {
                Icon(
                    painter = painterResource(moodInfo.mood.imageRes),
                    contentDescription = stringResource(id = R.string.descript_mood),
                    modifier = Modifier.padding(18.dp)
                )
            }
            Text(
                text = stringResource(id = moodInfo.mood.nameRes),
                fontSize = 10.sp,
                textAlign = TextAlign.Left,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}