package com.example.diceroller.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diceroller.databinding.SelectedsItemBinding

class SelectedsAdapter(private val selecteds: List<Int?>) : RecyclerView.Adapter<SelectedsAdapter.VHolder>() {

    inner class VHolder(binding: SelectedsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val layInf = LayoutInflater.from(parent.context)
        val binding = SelectedsItemBinding.inflate(layInf)

        return VHolder(binding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return selecteds.size
    }

}