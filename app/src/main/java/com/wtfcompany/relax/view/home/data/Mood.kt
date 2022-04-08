package com.wtfcompany.relax.view.home.data

import com.wtfcompany.relax.R

enum class Mood(val nameRes: Int, val imageRes: Int) {
    CALM(R.string.mood_calm, R.drawable.ic_calm),
    RELAX(R.string.mood_relax, R.drawable.ic_relax),
    FOCUS(R.string.mood_focus, R.drawable.ic_focus),
    EXCITED(R.string.mood_excited, R.drawable.ic_excited),
    FUN(R.string.mood_fun, R.drawable.ic_fun),
    SADNESS(R.string.mood_sadness, R.drawable.ic_sadness)
}