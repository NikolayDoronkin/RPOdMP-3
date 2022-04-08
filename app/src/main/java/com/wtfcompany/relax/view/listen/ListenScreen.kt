package com.wtfcompany.relax.view.listen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.wtfcompany.relax.R
import com.wtfcompany.relax.service.MusicService
import com.wtfcompany.relax.view.home.HomeViewModel
import org.koin.androidx.compose.get

@Composable
fun ListenScreen(navController: NavHostController) {

    val viewModel = get<HomeViewModel>()
    val context = LocalContext.current
    var musicService: MusicService? = null
    var isPlaying by remember { mutableStateOf(false) }

    val intent = Intent(context, MusicService::class.java)
    val moodRecommendation = viewModel.todayMood.name
    intent.putExtra("mood", moodRecommendation)
    context.startService(intent)

    val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            musicService = (binder as MusicService.LocalBinder).service
        }

        override fun onServiceDisconnected(name: ComponentName) {
            musicService?.stopSelf()
            musicService = null
        }
    }

    context.bindService(
        Intent(context, MusicService::class.java),
        connection,
        Context.BIND_AUTO_CREATE
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D3839))
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = stringResource(id = R.string.text_recommendation),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.text_music, moodRecommendation),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                color = Color.White
            )
            Box(
                modifier = Modifier
                    .padding(30.dp)
                    .background(color = Color.White)
            ) {
                Image(
                    painter = painterResource(viewModel.todayMood.imageRes),
                    contentDescription = stringResource(id = R.string.descript_music),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(30.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 50.dp)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_shuffle),
                    contentDescription = stringResource(id = R.string.descript_shuffle),
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { musicService?.shuffle() },
                    tint = Color.White,
                )
                Icon(
                    painter = painterResource(R.drawable.ic_skip_previous),
                    contentDescription = stringResource(id = R.string.descript_skip_previous),
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { musicService?.prev() },
                    tint = Color.White
                )
                Icon(
                    painter = if (isPlaying) painterResource(R.drawable.ic_pause)
                    else painterResource(R.drawable.ic_play),
                    contentDescription = stringResource(id = R.string.descript_pause),
                    modifier = Modifier
                        .size(55.dp)
                        .clickable {
                            musicService?.playPause()
                            isPlaying = !isPlaying
                        },
                    tint = Color.White
                )
                Icon(
                    painter = painterResource(R.drawable.ic_skip_next),
                    contentDescription = stringResource(id = R.string.descript_skip_next),
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { musicService?.next() },
                    tint = Color.White
                )
                Icon(
                    painter = painterResource(R.drawable.ic_repeat),
                    contentDescription = stringResource(id = R.string.descript_repeat),
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { musicService?.repeat() },
                    tint = Color.White
                )
            }
        }
    }
}
