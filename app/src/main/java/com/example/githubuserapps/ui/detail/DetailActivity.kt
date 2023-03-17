package com.example.githubuserapps.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapps.adapter.SectionsPagerAdapter
import com.example.githubuserapps.data.remote.response.UserResponse
import com.example.githubuserapps.data.token.ConstantToken.Companion.TAB_FOLLOWS
import com.example.githubuserapps.data.token.ConstantToken.Companion.USER_KEY
import com.example.githubuserapps.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private val detailBinding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailBinding.root)

        supportActionBar?.hide()

        val detailUsers = intent.extras?.get(USER_KEY) as String
        val sectionsPagerAdapter = SectionsPagerAdapter(
            this,
            detailUsers
        )
        val viewPager: ViewPager2 = detailBinding.vpTabsFollows
        viewPager.adapter  = sectionsPagerAdapter
        val tabFollows: TabLayout = detailBinding.tabsFollows
        TabLayoutMediator(
            tabFollows,
            viewPager
        ){ tabs, position ->
           tabs.text = resources.getString(TAB_FOLLOWS[position])
        }.attach()

        detailViewModel.apply {
            userdetail.observe(this@DetailActivity) {
                getUserDetailData(it)
            }
            isLoading.observe(this@DetailActivity) {
                showLoading(it)
            }
            getUserData(this@DetailActivity, detailUsers)
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
            Glide.with(this@DetailActivity)
                .load(userItems.avatarUrl)
                .circleCrop()
                .into(civItemAvatar)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        detailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}