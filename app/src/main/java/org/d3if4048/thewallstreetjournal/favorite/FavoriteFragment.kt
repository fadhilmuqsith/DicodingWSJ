package org.d3if4048.thewallstreetjournal.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.d3if4048.thewallstreetjournal.R
import org.d3if4048.thewallstreetjournal.core.ui.NewsAdapter
import org.d3if4048.thewallstreetjournal.detail.DetailNewsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){
            val newsAdapter = NewsAdapter()
            newsAdapter.onItemClick = {
                selectedData -> val intent = Intent(activity,DetailNewsActivity::class.java)
                intent.putExtra(DetailNewsActivity.EXTRA_DATA,selectedData)
                startActivity(intent)
            }


            favoriteViewModel.favoriteNews.observe(viewLifecycleOwner, Observer { dataNews ->
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
}