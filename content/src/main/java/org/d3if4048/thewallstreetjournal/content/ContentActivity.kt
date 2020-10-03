package org.d3if4048.thewallstreetjournal.content

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_content.*
import org.d3if4048.thewallstreetjournal.core.data.Resource
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class ContentActivity : AppCompatActivity() {
    private val contentViewModel : ContentViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        supportActionBar?.title = "WSJ Content"

        loadKoinModules(contenModule)

        getNewsData()


    }

    private fun getNewsData() {
        contentViewModel.news.observe(this, Observer { news ->
            if (news != null) {
                when (news){
                    is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        progress_bar.visibility = View.GONE
                        tv_content.text = "Here to see content of ${news.data?.get(0)?.title} \n ${news.data?.get(0)?.url.toString()}"
                    }
                    is Resource.Error -> {
                        progress_bar.visibility = View.GONE
                        tv_error.visibility = View.VISIBLE
                        tv_error.text = news.message
                    }
                }
            }
        })
    }
}