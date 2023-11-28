package com.example.diceroller.controllers.frags

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.diceroller.adapters.RollingAdapter
import com.example.diceroller.databinding.FragRollingBinding
import com.example.diceroller.databinding.ItemRollingBinding
import com.example.diceroller.repos.KeyRepo
import java.util.Random

class RollingFrag : Fragment() {

    private val binding by lazy {
        FragRollingBinding.inflate(layoutInflater)
    }

    private val recyView by lazy {
        binding.rollingRecyViewDices
    }

    private val rollFab by lazy {
        binding.rollingFabThrow
    }

    private lateinit var dices: List<Int>

    private lateinit var rollingAdapter: RollingAdapter

    private var resultList = mutableListOf<Int>()

    private val durationInMilli = 800L
    private val durationExtra = 200L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dices = arguments?.getIntArray(KeyRepo.DICE_KEY)!!.toList()
        dices = putEmpties(dices)

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
            resultList.clear()
            rolling()
            setTotalResult()
        }
    }

    private fun putEmpties(list: List<Int>) : List<Int> {
        val empty = 0
        val size = list.size
        val mutableList = mutableListOf<Int>()

        fun lastRow() {
            with(mutableList) {
                add(empty)
                add(list.last())
                add(empty)
            }
        }

        fun fillEvenRows (limit: Int) {
            for (i in 0 until limit step 2) {
                with(mutableList) {
                    add(list[i])
                    add(empty)
                    add(list[i + 1])
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

        return mutableList.toList()
    }

    private fun rolling() {

        fun rollDice(resultTxt: TextView, maxVal: Int, minVal: Int) {
            val random = Random()
            val resultDice = random.nextInt(maxVal) + minVal
            resultList.add(resultDice)
            resultTxt.text = resultDice.toString()
        }

        fun playValueAnimation(resultTxt: TextView, minVal: Int, maxVal: Int, durationInMilli: Long) {
            val valueAnimator = ValueAnimator.ofInt(minVal, maxVal)
            with(valueAnimator) {
                duration = durationInMilli
                addUpdateListener {
                    resultTxt.text = it.animatedValue.toString()
                }
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator) {
                    }

                    override fun onAnimationEnd(p0: Animator) {
                        rollDice(resultTxt, maxVal, minVal)
                    }

                    override fun onAnimationCancel(p0: Animator) {
                    }

                    override fun onAnimationRepeat(p0: Animator) {
                    }
                })
                start()
            }
        }

        fun rotateDiceImg(img: ImageView, rotation: Float, durationInMilli: Long) {
            img.animate()
                .rotationBy(rotation)
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

        }

        for (i in 0 until recyView.childCount) {
            val item = recyView.getChildAt(i)
            val listFromAdapter = rollingAdapter.dicesAndEmpties
            val isDice = listFromAdapter[i] != 0
            if (isDice) {
                val bindedItem = ItemRollingBinding.bind(item)

                val rotation = 1440f
                val img = bindedItem.rollitemImgTest
                rotateDiceImg(img, rotation, durationInMilli)

                val txt = bindedItem.rollitemTxtTest
                val minValue = 1
                val maxValue = listFromAdapter[i]
                playValueAnimation(txt, minValue, maxValue, durationInMilli)
            }
        }
    }

    private fun setTotalResult() {
        val handler = Handler()
        handler.postDelayed({
            binding.rollingTxtResult.text = resultList.sum().toString()
        }, durationInMilli + durationExtra)
    }

}