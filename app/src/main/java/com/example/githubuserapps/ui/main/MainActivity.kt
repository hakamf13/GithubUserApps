package com.example.githubuserapps.ui.main

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapps.R
import com.example.githubuserapps.adapter.ListUserAdapter
import com.example.githubuserapps.data.remote.response.ItemsItem
import com.example.githubuserapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        val layoutManager = LinearLayoutManager(this)
        mainBinding.rvGithubUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(
            this,
            layoutManager.orientation
        )
        mainBinding.rvGithubUser.addItemDecoration(itemDecoration)

        mainViewModel.search.observe(this) {
            setUserData(it)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.findItems(this)
    }

    private fun setUserData(userData: List<ItemsItem>) {
        val userAdapter = ListUserAdapter(userData)
        mainBinding.apply {
            rvGithubUser.setHasFixedSize(true)
            rvGithubUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithubUser.adapter = userAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        mainBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.appbar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = "Search an User"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    mainViewModel.searchUser(
                        this@MainActivity,
                        query
                    )
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        return super.onCreateOptionsMenu(menu)
    }
}