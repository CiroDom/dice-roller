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
    private val dicesAndEmpties: List<Int>,
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
        val dice = dicesAndEmpties[position]
        val isDice = dice != 0

        if (isDice) {
            selecitemImg.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorDice))
            selecitemTxt.text = "D${dice.toString()}"
        }
        else {
            selecitemImg.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.gray))
            selecitemTxt.text = ""
        }

        holder.itemView.setOnClickListener {
            if (!isDice && dicesAndEmpties.size >= 2) {
                removeSpace(position)
            }
            if (isDice) {
                removeDice(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return dicesAndEmpties.size
    }

}