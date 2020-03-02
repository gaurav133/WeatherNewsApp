package com.kanish.weathernewsapp.ui.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kanish.weathernewsapp.model.NewsItem
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
class NewsListAdapter(private val context: WeakReference<Context>,private val listener: AdapterCallBackListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val newsItemList = ArrayList<NewsItem>()
    private val HOLDER_SMALL = 0
    private val HOLDER_LARGE = 1
    var rand: Random = Random()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HOLDER_LARGE) {
            NewsItemHolderLarge.create(parent, context,listener)
        } else {
            NewsItemHolderSmall.create(parent, context,listener)
        }


    }

    override fun getItemCount(): Int = newsItemList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val newsItemItem = newsItemList[position]
        if (holder is NewsItemHolderSmall) {
            holder.bind(newsItemItem)
        } else if (holder is NewsItemHolderLarge) {

            holder.bind(newsItemItem)
        }

    }

    fun setArticlesResponse(articles: List<NewsItem>) {
        this.newsItemList.clear()
        this.newsItemList.addAll(articles)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        //Fixing a number ,since  response  json has not explicit key for large article, Can be changed by adding a key of large  to POJO at time of parsing
         //   and checking value of large ,if newsItem has large key return large view holder
        val randNUmber = 0
        return if (position==randNUmber) {
            HOLDER_LARGE

        }else{
            HOLDER_SMALL
        }

    }
}
