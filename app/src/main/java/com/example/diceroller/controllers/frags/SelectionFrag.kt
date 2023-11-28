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
import com.example.diceroller.controllers.actvs.MainActivity
import com.example.diceroller.R
import com.example.diceroller.adapters.DicesAdapter
import com.example.diceroller.adapters.SelectedsAdapter
import com.example.diceroller.databinding.FragSelectionBinding

class SelectionFrag : Fragment() {

    private val dices = listOf<Int>(2, 4, 6, 8, 10, 12, 20, 100)

    private val noDice = 0

    private val maxDiceQuant = 6

    private val selecteds = mutableListOf<Int>(noDice)

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
        val dicesAdapter = DicesAdapter(requireContext(), dices, ::addDice)

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

    private fun addDice(dice: Int) {
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

        val selectedsIntArray = selecteds.toIntArray()
        val bundle = Bundle()
        bundle.putIntArray(MainActivity.DICE_KEY, selectedsIntArray)

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