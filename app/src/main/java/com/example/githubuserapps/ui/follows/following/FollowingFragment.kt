package com.example.githubuserapps.ui.follows.following

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapps.adapter.ListUserAdapter
import com.example.githubuserapps.data.remote.response.ItemsItem
import com.example.githubuserapps.data.token.ConstantToken.Companion.USERNAME
import com.example.githubuserapps.data.token.ConstantToken.Companion.USER_KEY
import com.example.githubuserapps.databinding.FragmentFollowingBinding
import com.example.githubuserapps.ui.detail.DetailActivity
import com.example.githubuserapps.ui.detail.DetailViewModel
import com.example.githubuserapps.util.ViewModelFactory

class FollowingFragment : Fragment() {

    private var _followingBinding: FragmentFollowingBinding? = null
    private val followingBinding get() = _followingBinding!!

    private lateinit var followingViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _followingBinding = FragmentFollowingBinding.inflate(
            inflater,
            container,
            false
        )

        val followingFactory = ViewModelFactory(requireContext())
         followingViewModel = ViewModelProvider(
            requireActivity(),
            followingFactory
        )[DetailViewModel::class.java]

        followingViewModel.following.observe(viewLifecycleOwner) { followingData ->
            if (followingData == null) {
                val userData = arguments?.getString(USERNAME) ?: ""
                followingViewModel.getUserFollowing(requireActivity(), userData)
            } else {
                showFollowing(followingData)
            }
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        return followingBinding.root
    }

    private fun showFollowing(userData: List<ItemsItem>) {
        val followingAdapter = ListUserAdapter(userData)
        followingBinding.rvFragmentFollowing.layoutManager = LinearLayoutManager(activity)
        followingBinding.rvFragmentFollowing.adapter = followingAdapter
        followingAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val followingIntent = Intent(activity, DetailActivity::class.java)
                followingIntent.putExtra(USER_KEY, data.login)
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
