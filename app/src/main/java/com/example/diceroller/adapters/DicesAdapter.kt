package com.example.diceroller.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diceroller.databinding.DicesItemBinding

class DicesAdapter(private val dices: List<Int>) : RecyclerView.Adapter<DicesAdapter.VHolder>() {

    inner class VHolder(binding: DicesItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val layInf = LayoutInflater.from(parent.context)
        val hbinding = DicesItemBinding.inflate(layInf)

        return VHolder(hbinding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return dices.size
    }

}