package org.d3if4048.thewallstreetjournal.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_favorite.*
import org.d3if4048.thewallstreetjournal.core.ui.NewsAdapter
import org.d3if4048.thewallstreetjournal.detail.DetailNewsActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.title = "Favorite"

        loadKoinModules(favoriteModule)

        getFavoriteNews()

    }

    private fun getFavoriteNews() {
        val newsAdapter = NewsAdapter()
        newsAdapter.onItemClick = {
                selectedData -> val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra(DetailNewsActivity.EXTRA_DATA,selectedData)
            startActivity(intent)
        }

        favoriteViewModel.favoriteNews.observe(this, Observer { dataNews ->
            newsAdapter.setData(dataNews)
            view_empty.visibility = if (dataNews.isNotEmpty()) View.GONE else View.VISIBLE
        })
        with(rv_news){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

    }
}