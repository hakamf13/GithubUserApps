package com.example.githubuserapps.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapps.R
import com.example.githubuserapps.adapter.SectionsPagerAdapter
import com.example.githubuserapps.data.local.entity.FavoriteEntity
import com.example.githubuserapps.data.remote.response.UserResponse
import com.example.githubuserapps.data.token.ConstantToken.Companion.FAV_KEY
import com.example.githubuserapps.data.token.ConstantToken.Companion.TAB_FOLLOWS
import com.example.githubuserapps.data.token.ConstantToken.Companion.USER_KEY
import com.example.githubuserapps.databinding.ActivityDetailBinding
import com.example.githubuserapps.ext.loadImage
import com.example.githubuserapps.ext.setImageDrawableExt
import com.example.githubuserapps.util.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private val detailBinding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val detailFactory: ViewModelFactory by lazy {
        ViewModelFactory(this)
    }

    private val detailViewModel by lazy {
        ViewModelProvider(
            this,
            detailFactory
        )[DetailViewModel::class.java]
    }

    private val detailUsers: String by lazy {
        intent.extras?.getString(USER_KEY, "") ?: ""
    }

    private val favoriteEntity: FavoriteEntity by lazy {
        intent.getParcelableExtra<FavoriteEntity>(FAV_KEY) as FavoriteEntity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailBinding.root)

        if (savedInstanceState == null) {
            if (detailUsers.isNotEmpty()) {
                detailViewModel.getUserData(this, detailUsers)
            } else {
                detailViewModel.getUserData(this, favoriteEntity.username)
            }
        }

        initObserver()
        initView()
    }

    private fun initView() {
        supportActionBar?.hide()
    }

    private fun initObserver() {
        detailViewModel.apply {
            userDetail.observe(this@DetailActivity) {
                getUserDetailData(it)
            }
            isLoading.observe(this@DetailActivity) {
                showLoading(it)
            }
        }
    }

    private fun getUserDetailData(userItems: UserResponse) {
        detailBinding.apply {
            tvItemUsername.text = userItems.login
            tvItemName.text = userItems.name
            tvItemCompany.text = userItems.company
            tvItemLocation.text = userItems.location
            tvItemRepository.text = userItems.publicRepos.toString()
            tvItemFollowers.text = userItems.followers.toString()
            tvItemFollowing.text = userItems.following.toString()
            /*Glide.with(this@DetailActivity)
                .load(userItems.avatarUrl)
                .circleCrop()
                .into(civItemAvatar)*/
            civItemAvatar.loadImage(userItems.avatarUrl)

            val sectionsPagerAdapter = SectionsPagerAdapter(
                this@DetailActivity,
//                detailUsers
                userItems.login
            )
            TabLayoutMediator(tabsFollows, vpTabsFollows.apply {
                adapter = sectionsPagerAdapter
            }) { tabs, position ->
                tabs.text = resources.getString(TAB_FOLLOWS[position])
            }.attach()
            /*val viewPager: ViewPager2 = vpTabsFollows
            viewPager.adapter  = sectionsPagerAdapter
            val tabFollows: TabLayout = tabsFollows
            TabLayoutMediator(
                tabFollows,
                viewPager
            ){ tabs, position ->
                tabs.text = resources.getString(TAB_FOLLOWS[position])
            }.attach()*/

            detailViewModel.userFavorite.observe(this@DetailActivity) { favorite ->
                if (favorite.isNotEmpty()) {
                    val tempUser = favorite.find { it.username == detailUsers }
                    if (tempUser != null) {
                        imbFavorite.setImageDrawableExt(R.drawable.ic_favorite)
                        imbFavorite.setOnClickListener {
                            detailViewModel.deleteFavoriteUser(tempUser)
                        }
                    } else {
                        imbFavorite.setImageDrawableExt(R.drawable.ic_favorite_border_before_24)
                        imbFavorite.setOnClickListener {
                            detailViewModel.insertFavoriteUser(
                                userItems.id,
                                userItems.login,
                                userItems.avatarUrl
                            )
                        }
                    }
                } else {
                    imbFavorite.setImageDrawableExt(R.drawable.ic_favorite_border_before_24)
                    imbFavorite.setOnClickListener {
                        detailViewModel.insertFavoriteUser(
                            userItems.id,
                            userItems.login,
                            userItems.avatarUrl
                        )
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        detailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}