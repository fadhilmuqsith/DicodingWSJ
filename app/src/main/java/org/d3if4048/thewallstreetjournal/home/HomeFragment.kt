package org.d3if4048.thewallstreetjournal.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.view_error.*
import org.d3if4048.thewallstreetjournal.R
import org.d3if4048.thewallstreetjournal.core.data.Resource
import org.d3if4048.thewallstreetjournal.core.ui.NewsAdapter
import org.d3if4048.thewallstreetjournal.detail.DetailNewsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null){
            val newsAdapter = NewsAdapter()
            newsAdapter.onItemClick ={
                    selectedData -> val intent = Intent(activity,DetailNewsActivity::class.java)
                intent.putExtra(DetailNewsActivity.EXTRA_DATA,selectedData)
                startActivity(intent)
            }

            homeViewModel.news.observe(viewLifecycleOwner, Observer { news ->
                if (news != null){
                    when (news){
                        is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            progress_bar.visibility = View.GONE
                            newsAdapter.setData(news.data)
                        }
                        is Resource.Error -> {
                            progress_bar.visibility = View.GONE
                            view_error.visibility = View.VISIBLE
                            tv_error.text = news.message ?: getString(R.string.something_wrong)

                        }
                    }
                }
            })

            with(rv_news){
               layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = newsAdapter
            }
        }
    }
}