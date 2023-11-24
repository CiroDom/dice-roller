package com.example.diceroller.controllers

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.diceroller.MainActivity
import com.example.diceroller.adapters.RollingAdapter
import com.example.diceroller.databinding.FragRollingBinding
import com.example.diceroller.databinding.ItemRollingBinding

class RollingFrag : Fragment() {

    private lateinit var dices: List<Int>

    private val binding by lazy {
        FragRollingBinding.inflate(layoutInflater)
    }

    private val recyView by lazy {
        binding.rollingRecyViewDices
    }

    private lateinit var rollingAdapter: RollingAdapter

    private var nullFAB = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dices = arguments?.getIntArray(MainActivity.DICE_KEY)!!.toList()
        dices = putEmpties(dices)

        Log.i("dices", "$dices")

        setupRecyView(dices)
        setupRollFAB()
    }

    private fun setupRecyView(dices: List<Int>) {
        val spanCount = 3
        val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
        rollingAdapter = RollingAdapter(dices)

        with(recyView) {
            layoutManager = gridLayoutManager
            adapter = rollingAdapter
        }

        rollingAdapter.notifyDataSetChanged()
    }

    private fun setupRollFAB() {
        binding.rollingFabThrow.setOnClickListener {
            rollingAndResult()
        }
    }

    private fun rollTheDices(dices: List<Int>) {
        fun randomResult(dice: Int) : Int {
            return (0..dice).random()
        }


    }

    private fun putEmpties(dices: List<Int>) : List<Int> {
        val empty = 0
        val dicesAndEmpties = mutableListOf<Int>()
        val size = dices.size

        fun lastRow() {
            with(dicesAndEmpties) {
                add(empty)
                add(dices.last())
                add(empty)
            }
        }

        fun fillEvenRows (limit: Int) {
            for (i in 0 until limit step 2) {
                with(dicesAndEmpties) {
                    add(dices[i])
                    add(empty)
                    add(dices[i + 1])
                }
            }
        }

        if (size == 1) {
            lastRow()
        }
        else if (size % 2 != 0) {
            fillEvenRows(size - 1)
            lastRow()
        }
        else {
            fillEvenRows(size)
        }

        return dicesAndEmpties.toList()
    }

    private fun rollingAndResult() {
        for (i in 0 until recyView.childCount) {
            val dices = mutableListOf<Int>()
            val item = recyView.getChildAt(i)
            val bindedItem = ItemRollingBinding.bind(item)
//            val diceItem = if (bindedItem.)

            val img = bindedItem.rollitemImgTest
            img.animate()
                .rotationBy(720f)
                .duration = 500

            val txt = bindedItem.rollitemTxtTest

            val minValue = 1
            val maxValue = recyView
            val valueAnimator = ValueAnimator.ofInt()
        }
    }
}