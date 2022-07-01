package com.bravedeveloper.sandbase.presentation.slidingbanners

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bravedeveloper.sandbase.databinding.SlidingBannerItemBinding

class SlidingBannersAdapter(private val bannerList: List<SlidingBannerItem>) :
    RecyclerView.Adapter<SlidingBannersAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SlidingBannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: SlidingBannerItem) {
            binding.apply {
                slidingImage.setImageResource(banner.imageId)
                slidingText.setText(banner.descriptionId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            SlidingBannerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = bannerList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = bannerList.size
}