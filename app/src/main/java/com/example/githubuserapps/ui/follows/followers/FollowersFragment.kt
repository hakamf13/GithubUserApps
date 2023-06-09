package com.example.githubuserapps.ui.follows.followers

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
import com.example.githubuserapps.data.token.ConstantToken.Companion.USERNAME
import com.example.githubuserapps.data.token.ConstantToken.Companion.USER_KEY
import com.example.githubuserapps.databinding.FragmentFollowersBinding
import com.example.githubuserapps.ui.detail.DetailActivity

class FollowersFragment : Fragment() {

    private var _followersBinding: FragmentFollowersBinding? = null
    private val followersBinding get() = _followersBinding!!

    private val followersViewModel by viewModels<FollowersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _followersBinding = FragmentFollowersBinding.inflate(
            inflater,
            container,
            false
        )

        followersViewModel.followers.observe(viewLifecycleOwner) {
            if (it == null) {
                val userData = arguments?.getString(USERNAME) ?: ""
                followersViewModel.getUserFollowers(requireActivity(), userData)
            } else {
                showFollowers(it)
            }
        }

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        return followersBinding.root
    }

    private fun showFollowers(userData: List<ItemsItem>) {
        val followersAdapter = ListUserAdapter(userData)
        followersBinding.rvFragmentFollowers.layoutManager = LinearLayoutManager(activity)
        followersBinding.rvFragmentFollowers.adapter = followersAdapter
        followersAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val followersIntent = Intent(activity, DetailActivity::class.java)
                followersIntent.putExtra(USER_KEY, data.login)
                startActivity(followersIntent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        followersBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _followersBinding = null
    }

}
