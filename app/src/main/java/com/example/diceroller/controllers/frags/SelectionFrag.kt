package com.example.diceroller.controllers.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diceroller.R
import com.example.diceroller.adapters.DicesAdapter
import com.example.diceroller.adapters.SelectedsAdapter
import com.example.diceroller.databinding.FragSelectionBinding
import com.example.diceroller.repos.KeyRepo
import com.example.diceroller.utils.OurToast

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
        val dicesAdapter = DicesAdapter(dices, ::addDice)

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
                    selectedsAdapter.notifyDataSetChanged()
                    break
                }
            }
        }
        else if (selecteds.size < maxDiceQuant){
            selecteds.add(dice)

            updateDiceQuant()
        }
        else {
            maxDiceWarning()
            return
        }
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
        if (selecteds.size >= maxDiceQuant) {
            maxDiceWarning()
            return
        }

        selecteds.add(noDice)

        if (selecteds.size > 1) {
            diceOrDices.text = getString(R.string.dices)
        }
        updateDiceQuant()
    }

    private fun decreaseDiceQuant() {
        if (selecteds.size == 1) {
            selecteds[0] = noDice
            diceOrDices.text = getString(R.string.dice)
        }
        else if (selecteds.contains(noDice)) {
            selecteds.remove(noDice)
        } else {
            selecteds.removeLast()
        }

        updateDiceQuant()
    }

    private fun updateDiceQuant() {
        numberText.text = selecteds.size.toString()

        selectedsAdapter.notifyDataSetChanged()
    }

    private fun goToRollingActv() {
        if (selecteds.contains(noDice)) {
            val endMsg =
                if (selecteds.size == 1) getString(R.string.dice)
                else getString(R.string.dices)

            val msg = getString(R.string.toast_warning_number, selecteds.size, endMsg)

            OurToast.use(requireContext(), msg)

            return
        }

        val dicesIntArray = selecteds.toIntArray()
        val bundle = Bundle()
        bundle.putIntArray(KeyRepo.DICE_KEY, dicesIntArray,)

        val navController = findNavController()
        navController.navigate(R.id.nav_action_selec_to_roll, bundle)
    }

    private fun maxDiceWarning() {
        if (selecteds.size >= maxDiceQuant) {
            val msg = getString(R.string.toast_warning_max, maxDiceQuant)

            OurToast.use(requireContext(), msg)
        }
    }
}