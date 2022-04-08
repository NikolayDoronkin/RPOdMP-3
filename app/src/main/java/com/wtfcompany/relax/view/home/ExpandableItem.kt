package com.wtfcompany.relax.view.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wtfcompany.relax.R

@Composable
fun ExpandableItem(suggestionInfo: SuggestionInfo) {
    var expandableState by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = LinearOutSlowInEasing
                )
            ),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(
                text = suggestionInfo.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = suggestionInfo.description,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    if (expandableState)
                        Text(
                            text = suggestionInfo.content,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    Button(
                        modifier = Modifier
                            .width(150.dp),
                        onClick = { expandableState = !expandableState },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF2D3839),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = stringResource(
                                if (expandableState) R.string.btn_less else R.string.btn_more
                            )
                        )
                    }
                }

                Image(
                    painter = painterResource(suggestionInfo.image),
                    contentDescription = stringResource(id = R.string.descript_suggestion),
                    modifier = Modifier
                        .width(100.dp)
                        .padding(start = 10.dp)
                )
            }
        }
    }
}