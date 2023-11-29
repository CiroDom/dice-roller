package com.example.diceroller.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

class OurToast {

    companion object {

        fun use(context: Context, msg: String) {
            Toast.makeText(
                context,
                msg,
                Toast.LENGTH_SHORT
            ).apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }
        }
    }

}