package com.example.githubuserapps.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapps.adapter.FavoriteAdapter
import com.example.githubuserapps.data.di.FavoriteRepository
import com.example.githubuserapps.data.local.entity.FavoriteEntity
import com.example.githubuserapps.data.token.ConstantToken.Companion.FAV_KEY
import com.example.githubuserapps.data.token.ConstantToken.Companion.USER_KEY
import com.example.githubuserapps.databinding.ActivityFavoriteBinding
import com.example.githubuserapps.ui.detail.DetailActivity
import com.example.githubuserapps.util.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private val favoriteBinding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    private val favoriteFactory: ViewModelFactory by lazy {
        ViewModelFactory(this)
    }

    private val favoriteViewModel by lazy {
        ViewModelProvider(
            this,
            favoriteFactory
        )[FavoriteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(favoriteBinding.root)

        initView()
        initObserver()
    }

    private fun initView() {
        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        favoriteBinding.apply {

        }
    }

    private fun initObserver() {
        favoriteViewModel.apply {
            favUserList.observe(this@FavoriteActivity) { favorite ->
                favoriteBinding.progressBar.visibility = View.VISIBLE
                if (favorite.isEmpty()) {
                    favoriteBinding.progressBar.visibility = View.GONE
                } else {
                    favoriteBinding.progressBar.visibility = View.GONE
                }
                showFavorite(favorite)
            }
        }
    }

    private fun showFavorite(favorite: List<FavoriteEntity>) {
        val favoriteAdapter = FavoriteAdapter(
            favorite,
            FavoriteRepository(this)
        )
        favoriteBinding.rvFavoriteUser.layoutManager = LinearLayoutManager(this)
        favoriteBinding.rvFavoriteUser.adapter = favoriteAdapter
        favoriteAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteEntity) {
                val favoriteIntent = Intent(
                    this@FavoriteActivity,
                    DetailActivity::class.java
                )
                favoriteIntent.putExtra(USER_KEY, data)
                favoriteIntent.putExtra(FAV_KEY, data)
                startActivity(favoriteIntent)
            }
        })
    }
}