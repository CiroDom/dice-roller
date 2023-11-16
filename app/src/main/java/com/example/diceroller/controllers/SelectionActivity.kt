package com.example.diceroller.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diceroller.adapters.DicesAdapter
import com.example.diceroller.adapters.SelectedsAdapter
import com.example.diceroller.databinding.ActivitySelectionBinding

class SelectionActivity : AppCompatActivity() {

    private val dices = listOf<Int>(2, 4, 6, 8, 10, 12, 20, 100)

    private val apparentlySelecteds = mutableListOf<Int?>()

    private var diceQuant = 1

    private val binding by lazy {
        ActivitySelectionBinding.inflate(layoutInflater)
    }

    private val dicesRecyView by lazy {
        binding.selecRecyviewDices
    }

    private val selectedsRecyView by lazy {
        binding.selecRecyviewSelecteds
    }

    private val diceOrDices by lazy {
        binding.selecTxtDiceOrDices
    }

    private var selectedsAdapter = SelectedsAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupGridRecyView()
        setupHoriRecyView()

    }

    private fun setupGridRecyView() {
        val spanCount = 2
        val gridLayoutManager = GridLayoutManager(this, spanCount)
        val dicesAdapter = DicesAdapter(dices)

        with(dicesRecyView) {
            layoutManager = gridLayoutManager
            adapter = dicesAdapter
        }

        dicesAdapter.notifyDataSetChanged()
    }

    private fun setupHoriRecyView() {
        val initialList = mutableListOf<Int?>()
        for (i in 0 until diceQuant) {
            initialList.add(null)
        }
        selectedsAdapter = SelectedsAdapter(initialList)

        with(selectedsRecyView) {
            adapter = selectedsAdapter
            layoutManager = LinearLayoutManager(this@SelectionActivity)
        }
        
        selectedsAdapter.notifyDataSetChanged()
    }


    private fun addDice(dice: Int) {
        for (i in 0 until apparentlySelecteds.size) {
            if (i == null) {
                apparentlySelecteds[i] = dice
                break
            }
        }
    }

    private fun increseDiceQuant() {
        if (diceQuant >= 6) return

        if (diceQuant == 1) {
            diceOrDices.text = "dices"
        }

        diceQuant++
        
        if (diceQuant < apparentlySelecteds.size) {
            apparentlySelecteds.add(null)
        }
    }

    private fun decreaseDiceQuant() {
        if (diceQuant <= 1) return

        diceQuant--
        
        if (diceQuant > apparentlySelecteds.size) {
            apparentlySelecteds.removeLast()
        }

        if (diceQuant == 1) {
            diceOrDices.text = "dice"
        }
    }
}