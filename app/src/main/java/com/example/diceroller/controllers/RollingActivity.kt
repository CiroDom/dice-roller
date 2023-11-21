package com.example.diceroller.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.diceroller.adapters.RollingAdapter
import com.example.diceroller.databinding.ActivityRollingBinding

class RollingActivity : AppCompatActivity() {

    private lateinit var dices: List<Int>

    private val binding by lazy {
        ActivityRollingBinding.inflate(layoutInflater)
    }

    private val recyView by lazy {
        binding.rollingRecyViewDices
    }

    private lateinit var rollingAdapter: RollingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dices = intent.getIntArrayExtra("dices")!!.toList()
        setupRecyView(dices)
        setupFABThrow()
    }

    private fun setupRecyView(dices: List<Int>) {
        val spanCount = 3
        val gridLayoutManager = GridLayoutManager(this, spanCount)
        rollingAdapter = RollingAdapter(dices)

        with(recyView) {
            layoutManager = gridLayoutManager
            adapter = rollingAdapter
        }

        rollingAdapter.notifyDataSetChanged()
    }

    private fun setupFABThrow() {
        binding.rollingFabThrow
    }

    private fun rollTheDices(dices: List<Int>) {
        fun randomResult(dice: Int) : Int {
            return (0..dice).random()
        }


    }
}