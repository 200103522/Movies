package com.example.movies.Adapters

import android.content.Context
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
import com.example.movies.Adapters.SliderAdapter.SliderViewHolder
import com.example.movies.Domains.SliderItems
import com.example.movies.R

class SliderAdapter(sliderItems: MutableList<SliderItems>, viewPager2: ViewPager2) :
    RecyclerView.Adapter<SliderViewHolder>() {
    private val sliderItems: List<SliderItems>
    private val viewPager2: ViewPager2
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        context = parent.context
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position])
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView? = null

        init {
            var itemView = itemView
            itemView = itemView.findViewById(R.id.imageSlide)
        }

        fun setImage(sliderItems: SliderItems) {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(60))
            Glide.with(context!!)
                .load(sliderItems.image)
                .apply(requestOptions)
                .into(imageView!!)
        }
    }

    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

    init {
        this.sliderItems = sliderItems
        this.viewPager2 = viewPager2
    }
}
