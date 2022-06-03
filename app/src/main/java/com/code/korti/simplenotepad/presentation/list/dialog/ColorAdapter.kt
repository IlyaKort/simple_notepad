package com.code.korti.simplenotepad.presentation.list.dialog

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code.korti.simplenotepad.databinding.ItemColorBinding

class ColorAdapter(
    private var colors: Map<String, ColorDrawable>,
    private val onItemClick: (color: ColorDrawable) -> Unit,
) : RecyclerView.Adapter<ColorAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemColorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val color = colors.values.toList()[position]
        val name = colors.keys.toList()[position]
        holder.bind(name, color)
    }

    override fun getItemCount(): Int = colors.size

    class Holder(
        private val binding: ItemColorBinding,
        onItemClick: (color: ColorDrawable) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentColor: ColorDrawable? = null

        init {
            itemView.setOnClickListener {
                currentColor?.let { onItemClick(it) }
            }
        }

        fun bind(
            name: String,
            color: ColorDrawable
        ) {
            currentColor = color
            binding.colorName.text = name

            Glide.with(itemView)
                .load(color)
                .into(binding.imageView)
        }
    }
}