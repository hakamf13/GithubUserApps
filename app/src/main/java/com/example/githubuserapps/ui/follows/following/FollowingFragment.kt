package com.example.githubuserapps.ui.follows.following

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapps.adapter.ListUserAdapter
import com.example.githubuserapps.data.remote.response.ItemsItem
import com.example.githubuserapps.data.token.ConstantToken
import com.example.githubuserapps.databinding.FragmentFollowingBinding
import com.example.githubuserapps.ui.detail.DetailActivity

class FollowingFragment : Fragment() {

    private var _followingBinding: FragmentFollowingBinding? = null
    private val followingBinding get() = _followingBinding!!

    private val followingViewModel by viewModels<FollowingViewModel>()

//    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _followingBinding = FragmentFollowingBinding.inflate(
            inflater,
            container,
            false
        )

        followingViewModel.following.observe(viewLifecycleOwner) {
            if (it == null) {
                val userData = arguments?.getString(ConstantToken.USERNAME) ?: ""
                followingViewModel.getUserFollowing(requireActivity(), userData)
            } else {
                showFollowers(it)
            }
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        return followingBinding.root
    }

    private fun showFollowers(userData: List<ItemsItem>) {
        val followersAdapter = ListUserAdapter(userData)
        followingBinding.rvFragmentFollowing.layoutManager = LinearLayoutManager(activity)
        followingBinding.rvFragmentFollowing.adapter = followersAdapter
        followersAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val followingIntent = Intent(activity, DetailActivity::class.java)
                followingIntent.putExtra(ConstantToken.USER_KEY, data.login)
                startActivity(followingIntent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        followingBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _followingBinding = null
    }

}
