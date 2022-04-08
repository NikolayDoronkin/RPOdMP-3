package com.wtfcompany.relax.view.home

import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.wtfcompany.relax.App
import com.wtfcompany.relax.data.entity.UserMood
import com.wtfcompany.relax.view.base.BaseViewModel
import com.wtfcompany.relax.view.home.HomeContract.Effect.UpdateMoodList
import com.wtfcompany.relax.view.home.HomeContract.Effect.UpdateSuggestionList
import com.wtfcompany.relax.view.home.HomeContract.Event.OnMenuButtonClick
import com.wtfcompany.relax.view.home.HomeContract.Event.OnMoodButtonClick
import com.wtfcompany.relax.view.home.data.HoroscopeData
import com.wtfcompany.relax.view.home.data.Mood
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*


class HomeViewModel : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    var horoscopeData: HoroscopeData? = null
    var dailyMood: Mood = Mood.CALM
    var todayMood: Mood = Mood.CALM

    val moodData = listOf(
        MoodInfo(false, Mood.CALM),
        MoodInfo(false, Mood.RELAX),
        MoodInfo(false, Mood.FOCUS),
        MoodInfo(false, Mood.EXCITED),
        MoodInfo(false, Mood.FUN),
        MoodInfo(false, Mood.SADNESS)
    )

    init {
        getHoroscopeData()
        getDailyData()
    }

    private fun setActiveMood(mood: Mood) {
        moodData.onEach { it.isSelected = false }.first { it.mood == mood }.isSelected = true
        setEffect { UpdateMoodList(moodData) }
    }

    override fun createInitialState(): HomeContract.State {
        return HomeContract.State(
            HomeContract.HomeState.Idle
        )
    }

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is OnMenuButtonClick -> setState { copy(homeState = HomeContract.HomeState.Menu) }
            is OnMoodButtonClick -> {
                getTodayData(event.mood)
                setActiveMood(event.mood)
            }
        }
    }

    private fun getHoroscopeData() {
        viewModelScope.launch(Dispatchers.IO) {
            val sign = detectSign()
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign=$sign&day=today")
                .post(RequestBody.create(null, ByteArray(0)))
                .addHeader("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                .addHeader(
                    "x-rapidapi-key",
                    "f382cecaf5msh20615aae7f2daedp15743ajsnded8ea2e90bb"
                )
                .build()
            client.newCall(request).execute().use { response ->
                horoscopeData = Gson().fromJson(response.body!!.string(), HoroscopeData::class.java)
                setEffect {
                    UpdateSuggestionList(horoscopeData, todayMood, dailyMood)
                }
            }
        }
    }

    private fun detectSign(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = sdf.parse(App.instance.user!!.birthday)
        val cal: Calendar = Calendar.getInstance()
        cal.time = date!!
        val day = cal.get(Calendar.DATE)
        val month = cal.get(Calendar.MONTH) + 1
        return when {
            month == 12 && day >= 22 && day <= 31 || month == 1 && day >= 1 && day <= 19 -> "capricorn"
            month == 1 && day >= 20 && day <= 31 || month == 2 && day >= 1 && day <= 17 -> "aquarius"
            month == 2 && day >= 18 && day <= 29 || month == 3 && day >= 1 && day <= 19 -> "pisces"
            month == 3 && day >= 20 && day <= 31 || month == 4 && day >= 1 && day <= 19 -> "aries"
            month == 4 && day >= 20 && day <= 30 || month == 5 && day >= 1 && day <= 20 -> "taurus"
            month == 5 && day >= 21 && day <= 31 || month == 6 && day >= 1 && day <= 20 -> "gemini"
            month == 6 && day >= 21 && day <= 30 || month == 7 && day >= 1 && day <= 22 -> "cancer"
            month == 7 && day >= 23 && day <= 31 || month == 8 && day >= 1 && day <= 22 -> "leo"
            month == 8 && day >= 23 && day <= 31 || month == 9 && day >= 1 && day <= 22 -> "virgo"
            month == 9 && day >= 23 && day <= 30 || month == 10 && day >= 1 && day <= 22 -> "libra"
            month == 10 && day >= 23 && day <= 31 || month == 11 && day >= 1 && day <= 21 -> "scorpio"
            month == 11 && day >= 22 && day <= 30 || month == 12 && day >= 1 && day <= 21 -> "sagittarius"
            else -> "Illegal date"
        }
    }

    private fun getTodayData(mood: Mood) {
        saveMood(mood)
        todayMood = mood
        setEffect {
            UpdateSuggestionList(horoscopeData, todayMood, dailyMood)
        }
    }

    private fun saveMood(mood: Mood) {
        viewModelScope.launch {
            val userId = App.instance.user!!.id
            val sdf = SimpleDateFormat("dd:MM:yyyy", Locale.getDefault())
            val currentDate = sdf.format(Date())
            val userMood = UserMood(userId, currentDate, mood.ordinal)
            App.instance.userMoodRepository.insert(userMood)
        }
    }

    private fun getDailyData() {
        viewModelScope.launch {
            val userId = App.instance.user!!.id
            val map = mutableMapOf(
                Mood.CALM to 0,
                Mood.RELAX to 0,
                Mood.FOCUS to 0,
                Mood.EXCITED to 0,
                Mood.FUN to 0,
                Mood.SADNESS to 0,
            )
            val userMoods = App.instance.userMoodRepository.getUserMoods(userId)
            if (userMoods.isNotEmpty()) {
                val sdf = SimpleDateFormat("dd:MM:yyyy", Locale.getDefault())
                val currentDate = sdf.format(Date())
                for (userMood in userMoods) {
                    val mood = Mood.values()[userMood.mood]
                    if (userMood.date == currentDate) {
                        getTodayData(mood)
                        setActiveMood(mood)
                    }
                    map[mood] = map[mood]?.plus(1)!!
                }
            }
            val maxMood = map.maxByOrNull { it.value }!!
            if (maxMood.value != 0) {
                dailyMood = maxMood.key
                setEffect { UpdateSuggestionList(horoscopeData, todayMood, dailyMood) }
            }
        }
    }

    override fun clearState() {
        setState { copy(homeState = HomeContract.HomeState.Idle) }
    }
}