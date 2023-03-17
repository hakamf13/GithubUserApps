package com.example.githubuserapps.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapps.adapter.SectionsPagerAdapter
import com.example.githubuserapps.data.remote.response.UserResponse
import com.example.githubuserapps.data.token.ConstantToken.Companion.TAB_FOLLOWS
import com.example.githubuserapps.data.token.ConstantToken.Companion.USER_KEY
import com.example.githubuserapps.databinding.ActivityDetailBinding
import com.example.githubuserapps.utils.ViewModelFactory
import com.example.githubuserapps.utils.loadImage
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private val detailBinding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val detailViewModel by lazy {
        ViewModelProvider(
            this,
            detailFactory
        ) [DetailViewModel::class.java]
    }

    private val detailFactory: ViewModelFactory by lazy {
        ViewModelFactory(this)
    }

    private val detailUsers: String by lazy {
        intent.extras?.getString(USER_KEY, "") ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailBinding.root)

        if (savedInstanceState == null) {
            if (detailUsers.isNotEmpty()) {
                detailViewModel.getUserData(
                    this,
                    detailUsers
                )
            }
        }

        supportActionBar?.hide()

        detailViewModel.apply {
            userdetail.observe(this@DetailActivity) {
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
            tvItemEmail.text = userItems.email
            tvItemCompany.text = userItems.company
            tvItemLocation.text = userItems.location
            tvItemRepository.text = userItems.publicRepos.toString()
            tvItemFollowers.text = userItems.followers.toString()
            tvItemFollowing.text = userItems.following.toString()
            civItemAvatar.loadImage(userItems.avatarUrl)

            val sectionsPagerAdapter = SectionsPagerAdapter(
                this@DetailActivity,
                userItems.login
            )
            TabLayoutMediator(
                tabsFollows,
                vpTabsFollows.apply {
                    adapter = sectionsPagerAdapter
                }
            ) {tabs, position ->
                tabs.text = resources.getString(TAB_FOLLOWS[position])
            }.attach()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        detailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}