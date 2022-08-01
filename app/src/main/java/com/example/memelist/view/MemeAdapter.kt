package com.example.memelist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.memelist.databinding.LoadingListItemBinding
import com.example.memelist.databinding.MemeListItemBinding
import com.example.memelist.model.MemeItem

class MemeAdapter(
    private val list: MutableList<MemeItem?> = mutableListOf(),
    private val openDetails: (MemeItem) -> Unit
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setMemeList(newList: List<MemeItem>) {
        if(list.isNotEmpty()) list.removeLast()
        list.addAll(newList)
        list.add(null)
        notifyDataSetChanged()
    }

    inner class MemeViewHolder(private val binding: MemeListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun onBind(item: MemeItem) {
                binding.apply {
                    tvListTop.text = item.topText
                    tvListBottom.text = item.bottomText

                    Glide.with(ivListMeme)
                        .load(item.image)
                        .into(ivListMeme)
                }

                binding.root.setOnClickListener {
                    openDetails(item)
                }
            }
        }

    inner class LoadingViewHolder(private val binding: LoadingListItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    companion object {
        const val LOADING = 1
        const val MEME = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) LOADING else MEME
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            MEME -> {
                MemeViewHolder(
                    MemeListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                LoadingViewHolder(
                    LoadingListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MemeViewHolder -> holder.onBind(list[position]!!)
            is LoadingViewHolder -> { }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}