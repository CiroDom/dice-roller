package com.example.diceroller.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diceroller.R
import com.example.diceroller.adapters.DicesAdapter
import com.example.diceroller.adapters.SelectedsAdapter
import com.example.diceroller.databinding.ActivitySelectionBinding

class SelectionActivity : AppCompatActivity() {

    private val dices = listOf<Int>(2, 4, 6, 8, 10, 12, 20, 100)

    private val noDice = 0

    private val selecteds = mutableListOf<Int>(noDice)

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
        selectedsAdapter = SelectedsAdapter(selecteds, ::removeDice)

        with(selectedsRecyView) {
            layoutManager = LinearLayoutManager(this@SelectionActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = selectedsAdapter
        }
        
        selectedsAdapter.notifyDataSetChanged()
    }

    private fun addDice(dice: Int) {
        for (i in 0 until selecteds.size) {
            if (selecteds[i] == noDice) {
                selecteds[i] = dice
                break
            }
        }
        selectedsAdapter.notifyDataSetChanged()
    }

    private fun removeDice(dicePosition: Int) {
        selecteds[dicePosition] = noDice

        selectedsAdapter.notifyDataSetChanged()
    }

    private fun increseDiceQuant() {
        if (selecteds.size >= 6) return

        selecteds.add(noDice)

        if (selecteds.size > 1) {
            diceOrDices.text = getString(R.string.dices)
        }

        extendFAB.text = selecteds.size.toString()

        selectedsAdapter.notifyDataSetChanged()
    }

    private fun decreaseDiceQuant() {
        if (oneNoDice()) return

        if (selecteds.contains(noDice)) {
            selecteds.remove(noDice)
        } else if (selecteds.size >= 2) {
            selecteds.removeLast()
        } else {
            selecteds[0] = noDice
        }

        if (selecteds.size == 1) {
            diceOrDices.text = getString(R.string.dice)
        }

        extendFAB.text = selecteds.size.toString()

        selectedsAdapter.notifyDataSetChanged()
    }

    private fun goToRollingActv() {
        if (selecteds.contains(noDice)) {
            return
        }

        val pair = selecteds.size % 2 == 0
        val selectedsWithNoDice = mutableListOf<Int>()
        selectedsWithNoDice.addAll(selecteds)

        if (pair) {
            for (i in 0 until selecteds.size) {
                if (i % 2 != 0) {
                    selectedsWithNoDice.add(index = i, noDice)
                }
            }
        } else {
            for (i in 0 until selecteds.size) {
                if (i % 2 == 0) {
                    selectedsWithNoDice.add(index = i, noDice)
                }
            }
        }

        val selectedsIntArray = selectedsWithNoDice.toIntArray()
        val intent = Intent(this@SelectionActivity, RollingActivity::class.java)
        intent.putExtra("dices", selectedsIntArray)
        startActivity(intent)
    }

    private fun oneNoDice() : Boolean {
        if (
            selecteds.size == 1
            && selecteds.contains(noDice)
        ) {
            return true
        }

        return false
    }
}