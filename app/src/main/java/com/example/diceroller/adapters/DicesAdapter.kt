package com.example.diceroller.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.SoundEffectConstants
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.diceroller.databinding.ItemDicesBinding
import com.example.diceroller.models.Dice

class DicesAdapter(
    private val context: Context,
    private val dices: List<Dice>,
    private val addDice: (Dice) -> Unit) : RecyclerView.Adapter<DicesAdapter.VHolder>() {

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

            fun setImgDice(img: ImageView) {
                val drawableId = dice.drawableId
                val drawable = ContextCompat.getDrawable(context, drawableId)

                img.setImageDrawable(drawable)
            }

            fun add(view: View) {
                addDice(dice)

                view.playSoundEffect(SoundEffectConstants.CLICK)
            }

            setImgDice(diceitemImgDice)

            diceitemImgDice.setOnClickListener { imgView ->
                add(imgView)
            }

            with(diceitemTxtDice) {
                setOnClickListener { txtView ->
                    add(txtView)
                }
                text = dice.sides.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return dices.size
    }

}