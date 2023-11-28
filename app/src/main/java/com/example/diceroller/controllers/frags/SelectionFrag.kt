package com.example.diceroller.controllers.frags

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diceroller.R
import com.example.diceroller.adapters.DicesAdapter
import com.example.diceroller.adapters.SelectedsAdapter
import com.example.diceroller.databinding.FragSelectionBinding
import com.example.diceroller.models.Dice
import com.example.diceroller.repos.DiceRepo
import com.example.diceroller.repos.KeyRepo

class SelectionFrag : Fragment() {

    private val dices = DiceRepo().dices

    private val noDice = dices[0]

    private val maxDiceQuant = 6

    private val selecteds = mutableListOf<Dice>(noDice)

    private val binding by lazy {
        FragSelectionBinding.inflate(layoutInflater)
    }

    private val dicesRecyView by lazy {
        binding.selecRecyviewDices
    }

    private val selectedsRecyView by lazy {
        binding.selecRecyviewSelecteds
    }

    private val numberText by lazy {
        binding.selecTxtNumberDices
    }

    private val diceOrDices by lazy {
        binding.selecTxtDiceOrDices
    }

    private lateinit var selectedsAdapter: SelectedsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
        val actualDices = dices - dices[0]
        val dicesAdapter = DicesAdapter(requireContext(), actualDices, ::addDice)

        with(dicesRecyView) {
            layoutManager = gridLayoutManager
            adapter = dicesAdapter
        }

        dicesAdapter.notifyDataSetChanged()
    }

    private fun setupHoriRecyView() {
        selectedsAdapter = SelectedsAdapter(requireContext(), selecteds, ::removeDice, ::removeSpace)

        with(selectedsRecyView) {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = selectedsAdapter
        }
        
        selectedsAdapter.notifyDataSetChanged()
    }

    private fun addDice(dice: Dice) {
        if (selecteds.contains(noDice)) {
            for (i in 0 until selecteds.size) {
                if (selecteds[i] == noDice) {
                    selecteds[i] = dice
                    break
                }
            }
        }
        else if (selecteds.size < maxDiceQuant){
            selecteds.add(dice)

            updateDiceQuant()
        }

        selectedsAdapter.notifyDataSetChanged()
    }

    private fun removeDice(dicePosition: Int) {
        selecteds[dicePosition] = noDice

        selectedsAdapter.notifyDataSetChanged()
    }

    private fun removeSpace(position: Int) {
        selecteds.removeAt(position)

        updateDiceQuant()
    }

    private fun increseDiceQuant() {
        if (selecteds.size >= maxDiceQuant) return

        selecteds.add(noDice)

        if (selecteds.size > 1) {
            diceOrDices.text = getString(R.string.dices)
        }
        updateDiceQuant()
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
        updateDiceQuant()
    }

    private fun updateDiceQuant() {
        numberText.text = selecteds.size.toString()

        selectedsAdapter.notifyDataSetChanged()
    }

    private fun goToRollingActv() {
        if (selecteds.contains(noDice)) {
            val msgEnd =
                if (selecteds.size == 1) getString(R.string.dice)
                else getString(R.string.dices)

            Toast.makeText(
                requireContext(),
                getString(R.string.toast_warning, selecteds.size, msgEnd),
                Toast.LENGTH_SHORT
            ).apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }

            return
        }

        val bundle = Bundle()
        val dicesIntArray = selecteds.map { dice -> dice.sides }.toIntArray()
        bundle.putIntArray(KeyRepo.DICE_KEY, dicesIntArray,)
        val drawableIdsIntArray = selecteds.map { dice -> dice.drawableId }.toIntArray()
        bundle.putIntArray(KeyRepo.DICE_DRAW_ID_KEY, drawableIdsIntArray)

        val navController = findNavController()
        navController.navigate(R.id.nav_action_selec_to_roll, bundle)
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