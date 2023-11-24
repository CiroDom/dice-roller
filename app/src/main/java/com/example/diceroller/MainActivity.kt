package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.diceroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val DICE_KEY = "dices"
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFrag =
            supportFragmentManager.findFragmentById(R.id.main_frag_host) as NavHostFragment
        val navController = navHostFrag.navController

        /** TALVEZ REMOVER ISSO ACIMA DEPOIS*/

    }

}