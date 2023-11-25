package com.example.diceroller.controllers

import android.animation.Animator
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
import java.util.Random

class RollingFrag : Fragment() {

    private lateinit var dices: List<Int>

    private val binding by lazy {
        FragRollingBinding.inflate(layoutInflater)
    }

    private val recyView by lazy {
        binding.rollingRecyViewDices
    }

    private val rollFab by lazy {
        binding.rollingFabThrow
    }

    private lateinit var rollingAdapter: RollingAdapter

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
        rollFab.setOnClickListener {
            rollingAndResult()
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
            val item = recyView.getChildAt(i)
            val bindedItem = ItemRollingBinding.bind(item)
            val durationInMilli = 800L

            val img = bindedItem.rollitemImgTest
            img.animate()
                .rotationBy(1440f)
                .setDuration(durationInMilli)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator) {
                        rollFab.isClickable = false
                    }

                    override fun onAnimationEnd(p0: Animator) {
                        rollFab.isClickable = true
                    }

                    override fun onAnimationCancel(p0: Animator) {
                    }

                    override fun onAnimationRepeat(p0: Animator) {
                    }


                })

            val listFromAdapter = rollingAdapter.dicesAndEmpties
            val isDice = listFromAdapter[i] != 0
            if (isDice) {
                val txt = bindedItem.rollitemTxtTest
                val minValue = 1
                val maxValue = listFromAdapter[i]

                val valueAnimator = ValueAnimator.ofInt(minValue, maxValue)
                with(valueAnimator) {
                    duration = durationInMilli
                    addUpdateListener {
                        txt.text = it.animatedValue.toString()
                    }
                    addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(p0: Animator) {
                        }

                        override fun onAnimationEnd(p0: Animator) {
                            val random = Random()
                            val randomNumber = random.nextInt(maxValue) + minValue
                            txt.text = randomNumber.toString()
                        }

                        override fun onAnimationCancel(p0: Animator) {
                        }

                        override fun onAnimationRepeat(p0: Animator) {
                        }
                    })
                    start()
                }
            }
        }
    }
}