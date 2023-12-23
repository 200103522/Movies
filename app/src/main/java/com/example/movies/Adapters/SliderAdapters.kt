package com.example.movies.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movies.Domain.SliderItems
import com.example.movies.R

class SliderAdapters(
    private val sliderItems: MutableList<SliderItems>,
    private val viewPager2: ViewPager2
) :
    RecyclerView.Adapter<SliderAdapters.SliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.slider_item_container, parent, false
        )
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(sliderItems[position])
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int = sliderItems.size

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlide)

        fun bind(sliderItem: SliderItems) {
            val requestOptions = RequestOptions().transforms(CenterCrop(), RoundedCorners(60))
            Glide.with(itemView.context)
                .load(sliderItem.image)
                .apply(requestOptions)
                .into(imageView)
        }
    }

    private val runnable = Runnable {
        val duplicatedItems = ArrayList(sliderItems)
        sliderItems.addAll(duplicatedItems)
        notifyItemInserted(sliderItems.size - duplicatedItems.size)
    }
}
