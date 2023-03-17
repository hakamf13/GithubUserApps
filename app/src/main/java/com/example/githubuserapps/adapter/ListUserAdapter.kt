package com.example.githubuserapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapps.data.remote.response.ItemsItem
import com.example.githubuserapps.databinding.ItemRowUserBinding
import com.example.githubuserapps.utils.loadImage

class ListUserAdapter(private val listUser: List<ItemsItem>): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var itemRowUserBinding: ItemRowUserBinding): RecyclerView.ViewHolder(itemRowUserBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemRowUserBinding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemRowUserBinding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .circleCrop()
            .into(holder.itemRowUserBinding.ivImgAvatar)
        holder.itemRowUserBinding.tvTextUsername.text = user.login
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.absoluteAdapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

}