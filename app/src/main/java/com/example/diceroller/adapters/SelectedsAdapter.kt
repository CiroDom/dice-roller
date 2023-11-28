package com.example.diceroller.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.diceroller.R
import com.example.diceroller.databinding.ItemSelectedsBinding

class SelectedsAdapter(
    private val context: Context,
    private val selecteds: MutableList<Int>,
    private val removeDice: (Int) -> Unit,
    private val removeSpace: (Int) -> Unit
    ) : RecyclerView.Adapter<SelectedsAdapter.VHolder>() {

    inner class VHolder(val binding: ItemSelectedsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val layInf = LayoutInflater.from(parent.context)
        val binding = ItemSelectedsBinding.inflate(layInf)

        return VHolder(binding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val selecitemImg = holder.binding.selecitemImgSelecteds
        val selecitemTxt = holder.binding.selectitemTxtSelecteds
        val dice = selecteds[position]
        val isDice = dice != 0

        if (isDice) {
            selecitemImg.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.blue_primary))
            selecitemTxt.text = dice.toString()
        }
        else {
            selecitemImg.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray))
            selecitemTxt.text = ""
        }

        holder.itemView.setOnClickListener {
            if (dice == 0 && selecteds.size >= 2) {
                removeSpace(position)
            }
            if (dice != 0) {
                removeDice(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return selecteds.size
    }

}