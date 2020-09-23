package org.d3if4048.thewallstreetjournal.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_news.*
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import kotlinx.android.synthetic.main.content_detail_news_other.*
import org.d3if4048.thewallstreetjournal.R
import org.d3if4048.thewallstreetjournal.core.domain.model.News
import org.koin.android.viewmodel.ext.android.viewModel

class DetailNewsActivity : AppCompatActivity() {

    private val detailNewsViewModel: DetailNewsViewModel by viewModel()

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)
        setSupportActionBar(toolbar)


        val detailNews = intent.getParcelableExtra<News>(EXTRA_DATA)
        showDetailNews(detailNews)
    }

    private fun showDetailNews(detailNews : News?){
        detailNews?.let {
            var simpleDate = detailNews.publishedAt.substring(0,10)
            if (detailNews.author != null )  {
                supportActionBar?.title = detailNews.author
            } else {
                supportActionBar?.title = "Unknown Author"
            }


            tv_detail_title.text = detailNews.title
            tv_detail_date.text = simpleDate
            tv_detail_description.text = detailNews.description
            tv_detail_link.text = detailNews.url
            Glide.with(this@DetailNewsActivity).load(detailNews.urlToImage).into(text_detail_image)
            var statusFavorite =detailNews.isFavorite
            setStatusFavorite(statusFavorite)
            fab.setOnClickListener {
                statusFavorite = !statusFavorite
                detailNewsViewModel.setFavoriteNews(detailNews,statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite:Boolean) {
        if (statusFavorite) {
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
        }

    }
}