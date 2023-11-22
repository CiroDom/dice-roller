package com.example.diceroller.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diceroller.databinding.ItemSelectedsBinding

class SelectedsAdapter(
    private val selecteds: MutableList<Int>,
    private val removeDice: (Int) -> Unit) : RecyclerView.Adapter<SelectedsAdapter.VHolder>() {

    inner class VHolder(val binding: ItemSelectedsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val layInf = LayoutInflater.from(parent.context)
        val binding = ItemSelectedsBinding.inflate(layInf)

        return VHolder(binding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val selecitemTxtSelecteds = holder.binding.selectitemTxtSelecteds
        val dice = selecteds[position]

        selecitemTxtSelecteds.text =
            if (dice != 0) {
                dice.toString()
            } else {
                ""
            }

        holder.itemView.setOnClickListener {
            removeDice(position)
        }
    }

    override fun getItemCount(): Int {
        return selecteds.size
    }

}