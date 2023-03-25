package com.example.githubuserapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuserapps.data.di.FavoriteRepository
import com.example.githubuserapps.data.local.entity.FavoriteEntity
import com.example.githubuserapps.databinding.ItemRowFavoriteBinding

class FavoriteAdapter(
    private val favoriteList: List<FavoriteEntity>,
    private val favoriteRepository: FavoriteRepository
): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class FavoriteViewHolder(var favoriteBinding: ItemRowFavoriteBinding) :
        RecyclerView.ViewHolder(favoriteBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val favoriteBinding = ItemRowFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(favoriteBinding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteUser = favoriteList[position]
        /*Glide.with(holder.itemView.context)
            .load(favoriteUser.avatarUrl)
            .circleCrop()
            .into(holder.favoriteBinding.ivImgAvatar)*/
        holder.favoriteBinding.ivImgAvatar.loadImage(favoriteUser.avatarUrl)
        holder.favoriteBinding.tvTextUsername.text = favoriteUser.username
        holder.favoriteBinding.btnDelete.setOnClickListener {
            favoriteRepository.deleteFavoriteUser(favoriteUser)
        }
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(favoriteList[holder.absoluteAdapterPosition])
        }
    }

    private fun ImageView.loadImage(avatarUrl: String?) {
        Glide.with(this.context) .load(avatarUrl) .apply(
                RequestOptions().override(200, 200)
            ) .centerCrop() .circleCrop() .into(this)
    }

    override fun getItemCount(): Int = favoriteList.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteEntity)
    }

}