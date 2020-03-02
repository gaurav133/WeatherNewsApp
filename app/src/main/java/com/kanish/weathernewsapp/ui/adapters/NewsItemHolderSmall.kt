package com.kanish.weathernewsapp.ui.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

import com.kanish.weathernewsapp.R
import com.kanish.weathernewsapp.model.NewsItem
import java.lang.ref.WeakReference

/**
 * Created by Kanish Roshan on 2020-03-01.
 */

class NewsItemHolderSmall(view: View) : RecyclerView.ViewHolder(view) {
    var imageView: ImageView = view.findViewById(R.id.news_image)
    var textView: TextView = view.findViewById(R.id.tv_news_title)
    var tvNewsDetails: TextView = view.findViewById(R.id.tv_news_details)
    var tvNewsLink: TextView = view.findViewById(R.id.tv_news_link)
    var tvNewsTime: TextView = view.findViewById(R.id.tv_news_time)
    var tvNewsCat: TextView = view.findViewById(R.id.tv_news_cat)


    private var newsItemData: NewsItem? = null

    init {
        view.setOnClickListener {
            newsItemData?.target_url?.let { url ->
              /*  val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)*/
                mListener?.onItemClickListener(url)
            }
        }
    }

    fun bind(repo: NewsItem?) {
        if (repo == null) {
            val resources = itemView.resources
            textView.text = resources.getString(R.string.loading)
            tvNewsDetails.text=resources.getString(R.string.loading)
            tvNewsCat.text=resources.getString(R.string.loading)
        } else {
            showRepoData(repo)
        }
    }

    private fun showRepoData(data: NewsItem) {
        newsItemData=data
        textView.text = data.title
       tvNewsDetails.text=data.description
       tvNewsCat.text=data.tag
        var tUrl=data.target_url
        when {
            tUrl.contains("https://www.") -> {
                tUrl=tUrl.replace("https://www.","")
            }
            tUrl.contains("https://") -> {
                tUrl=tUrl.replace("https://","")
            }
            tUrl.contains("http://www") -> {
                tUrl=tUrl.replace("http://","")
            }
        }
      tvNewsLink.text=tUrl
        var url=""
        url += data.image_url
        mContext?.get()?.let {
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(it)
                .load(url)
                .dontAnimate()
                .apply(requestOptions)
                .placeholder(ContextCompat.getDrawable(it,R.drawable.ic_news_placeholder_small))
                .into(imageView)
        }
    }

    companion object {
        private var mContext: WeakReference<Context>? = null
        private var mListener: AdapterCallBackListener? = null
        fun create(
            parent: ViewGroup,
            context: WeakReference<Context>?,
            listener: AdapterCallBackListener?
        ): NewsItemHolderSmall {
            mContext =context
            mListener=listener
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item_response, parent, false)

            return NewsItemHolderSmall(view)
        }
    }
}
