package com.example.diceroller.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diceroller.databinding.ItemRollingBinding

class RollingAdapter(private val diceList: List<Int>,) : RecyclerView.Adapter<RollingAdapter.VHolder>() {

    private val pair = diceList.size % 2 == 0

    inner class VHolder(val binding: ItemRollingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val layInf = LayoutInflater.from(parent.context)
        val binding = ItemRollingBinding.inflate(layInf)

        return VHolder(binding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val txt = holder.binding.rollitemTxtTest
        val img = holder.binding.rollitemImgTest
        val isDice = diceList[position] != 0

        if (isDice){
            txt.text = diceList[position].toString()
        } else {
            txt.text = null
            img.setImageDrawable(null)
        }
    }

    override fun getItemCount(): Int {
        return diceList.size
    }
}