package com.example.diceroller.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diceroller.R
import com.example.diceroller.adapters.DicesAdapter
import com.example.diceroller.adapters.SelectedsAdapter
import com.example.diceroller.databinding.ActivitySelectionBinding

class SelectionActivity : AppCompatActivity() {

    private val dices = listOf<Int>(2, 4, 6, 8, 10, 12, 20, 100)

    private val noDice = 0

    private val apparentlySelecteds = mutableListOf<Int>(noDice)

    private val binding by lazy {
        ActivitySelectionBinding.inflate(layoutInflater)
    }

    private val dicesRecyView by lazy {
        binding.selecRecyviewDices
    }

    private val selectedsRecyView by lazy {
        binding.selecRecyviewSelecteds
    }

    private val extendFAB by lazy {
        binding.selecFabNext
    }

    private val diceOrDices by lazy {
        binding.selecTxtDiceOrDices
    }

    private lateinit var selectedsAdapter: SelectedsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupGridRecyView()
        setupHoriRecyView()
        setupFABs()
    }

    private fun setupFABs() {
        with(binding) {
            selecFabMinus.setOnClickListener {
                decreaseDiceQuant()
            }

            selecFabPlus.setOnClickListener{
                increseDiceQuant()
            }

            selecFabNext.setOnClickListener {
                goToRollingActv()
            }
        }
    }

    private fun setupGridRecyView() {
        val spanCount = 2
        val gridLayoutManager = GridLayoutManager(this, spanCount)
        val dicesAdapter = DicesAdapter(dices, ::addDice)

        with(dicesRecyView) {
            layoutManager = gridLayoutManager
            adapter = dicesAdapter
        }

        dicesAdapter.notifyDataSetChanged()
    }

    private fun setupHoriRecyView() {
        selectedsAdapter = SelectedsAdapter(apparentlySelecteds, ::removeDice)

        with(selectedsRecyView) {
            layoutManager = LinearLayoutManager(this@SelectionActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = selectedsAdapter
        }
        
        selectedsAdapter.notifyDataSetChanged()
    }

    private fun addDice(dice: Int) {
        for (i in 0 until apparentlySelecteds.size) {
            if (apparentlySelecteds[i] == noDice) {
                apparentlySelecteds[i] = dice
                break
            }
        }

        selectedsAdapter.notifyDataSetChanged()

        Log.i("addDice", "$apparentlySelecteds")
    }

    private fun removeDice(dicePosition: Int) {
        apparentlySelecteds[dicePosition] = noDice

        selectedsAdapter.notifyDataSetChanged()

        Log.i("removeDice", "$apparentlySelecteds")
    }

    private fun increseDiceQuant() {
        if (apparentlySelecteds.size >= 6) return

        apparentlySelecteds.add(noDice)

        if (apparentlySelecteds.size > 1) {
            diceOrDices.text = getString(R.string.dices)
        }

        extendFAB.text = apparentlySelecteds.size.toString()

        selectedsAdapter.notifyDataSetChanged()

        Log.i("increase", "$apparentlySelecteds")
    }

    private fun decreaseDiceQuant() {
        if (apparentlySelecteds.size <= 1) return

        if (apparentlySelecteds.contains(noDice)) {
            apparentlySelecteds.remove(noDice)
        } else {
            apparentlySelecteds.removeLast()
        }

        if (apparentlySelecteds.size == 1) {
            diceOrDices.text = getString(R.string.dice)
        }

        extendFAB.text = apparentlySelecteds.size.toString()

        selectedsAdapter.notifyDataSetChanged()

        Log.i("decrease", "$apparentlySelecteds")
    }

    private fun goToRollingActv() {
        if (apparentlySelecteds.contains(noDice)) {
            Log.i("goTo", "bloquado")
            return
        }

        Log.i("goTo", "chegou aqui")

        val pair = apparentlySelecteds.size % 2 == 0

        if (pair) {
            for (i in 0 until apparentlySelecteds.size) {
                if (i % 2 != 0) {
                    apparentlySelecteds.add(index = i, noDice)
                }
            }
        } else {
            for (i in 0 until apparentlySelecteds.size) {
                if (i % 2 == 0) {
                    apparentlySelecteds.add(index = i, noDice)
                }
            }
        }

        val actualSelecteds = apparentlySelecteds.toIntArray()
        val intent = Intent(this@SelectionActivity, RollingActivity::class.java)
        intent.putExtra("dices", actualSelecteds)
    }
}