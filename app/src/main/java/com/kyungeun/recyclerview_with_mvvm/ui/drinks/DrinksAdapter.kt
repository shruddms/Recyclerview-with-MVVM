package com.kyungeun.recyclerview_with_mvvm.ui.drinks

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.kyungeun.recyclerview_with_mvvm.R
import com.kyungeun.recyclerview_with_mvvm.data.entities.Drink
import com.kyungeun.recyclerview_with_mvvm.databinding.ItemDrinkBinding

class DrinksAdapter(private val listener: DrinkItemListener) : RecyclerView.Adapter<DrinksAdapter.DrinkViewHolder>() {

    interface DrinkItemListener {
        fun onClickedDrink(drinkId: Int)
    }

    private val items = ArrayList<Drink>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<Drink>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val binding: ItemDrinkBinding = ItemDrinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinkViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) = holder.bind(items[position])

    inner class DrinkViewHolder(private val itemBinding: ItemDrinkBinding, private val listener: DrinkItemListener) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var user: Drink

        init {
            itemBinding.root.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Drink) {
            this.user = item
            itemBinding.category.text = item.category
            itemBinding.alcoholic.text = item.alcoholic
            itemBinding.name.text = item.name

            // Set the signature to be the last modified time of the image file.
            val imageMetadata = item.dateModified?.let { ObjectKey(it) } ?: ObjectKey("")

            Glide.with(itemBinding.root.context)
                .load(item.image)
                .override(512, 512)
                .dontAnimate()
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .signature(imageMetadata)
                .into(itemBinding.image)
        }

        override fun onClick(v: View?) {
            listener.onClickedDrink(user.id!!)
        }
    }
}
