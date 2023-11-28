package com.example.diceroller.repos

import android.content.Context
import com.example.diceroller.R
import com.example.diceroller.models.Dice

class DiceRepo {

    val dices = listOf<Dice>(
        Dice(0, R.color.gray, R.drawable.baseline_casino),
        Dice(2, R.color.colorD2, R.drawable.d2_128),
        Dice(4, R.color.colorD4, R.drawable.d4_128),
        Dice(6, R.color.colorD6, R.drawable.d6_128),
        Dice(8, R.color.colorD8, R.drawable.d8_128),
        Dice(10, R.color.colorD10, R.drawable.d10_128),
        Dice(12, R.color.colorD12, R.drawable.d12_128),
        Dice(20, R.color.colorD20, R.drawable.d20_128),
        Dice(100, R.color.colorD100, R.drawable.d100_128),
    )

}