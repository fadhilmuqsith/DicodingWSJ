package org.d3if4048.thewallstreetjournal.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_list_news.view.*
import org.d3if4048.thewallstreetjournal.R
import org.d3if4048.thewallstreetjournal.core.data.source.local.entity.NewsEntity
import org.d3if4048.thewallstreetjournal.core.domain.model.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ListViewHolder>(){

    private var listData = ArrayList<News>()
    var onItemClick : ((News) -> Unit)? = null

    fun setData(newListData : List<News>?){
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_news,parent,false))


    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: News) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .into(iv_item_image)
                tv_item_title.text = data.title
                if (data.author != "null" )  {
                    tv_item_subtitle.text =  data.author
                } else {
                    tv_item_subtitle.text = "Unknown Author"
                }
            }
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }


}