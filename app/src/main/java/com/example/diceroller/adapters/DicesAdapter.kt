package com.example.diceroller.adapters

import android.view.LayoutInflater
import android.view.SoundEffectConstants
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diceroller.databinding.ItemDicesBinding

class DicesAdapter(private val dices: List<Int>, private val addDice: (Int) -> Unit) : RecyclerView.Adapter<DicesAdapter.VHolder>() {

    inner class VHolder(val binding: ItemDicesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val layInf = LayoutInflater.from(parent.context)
        val hbinding = ItemDicesBinding.inflate(layInf)

        return VHolder(hbinding)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val hbinding = holder.binding
        val dice = dices[position]

        with(hbinding) {
            fun add(view: View) {
                val dice = dices[position]
                addDice(dice)

                view.playSoundEffect(SoundEffectConstants.CLICK)
            }

            diceitemImgDice.setOnClickListener { imgView ->
                add(imgView)
            }

            with(diceitemTxtDice) {
                setOnClickListener { txtView ->
                    add(txtView)
                }
                text = dice.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return dices.size
    }

}