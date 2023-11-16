package com.example.diceroller.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diceroller.databinding.RollingsItemBinding

class RollingsAdapter(private val rollings: List<Int>) : RecyclerView.Adapter<RollingsAdapter.VHolder>() {

    inner class VHolder(binding: RollingsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val layInf = LayoutInflater.from(parent.context)
        val binding = RollingsItemBinding.inflate(layInf)

        return VHolder(binding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return rollings.size
    }

}