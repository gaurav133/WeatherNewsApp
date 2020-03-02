package com.kanish.weathernewsapp.ui.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kanish.weathernewsapp.R
import com.kanish.weathernewsapp.model.NewsItem
import java.lang.Exception
import java.lang.ref.WeakReference
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
class NewsItemHolderLarge (view: View) : RecyclerView.ViewHolder(view) {
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
        newsItemData = data
        textView.text = data.title
        tvNewsDetails.text = data.description
        tvNewsCat.text = data.tag
        //time data  from api fatal: needs to be split and formatted before using it for time difference
      //  tvNewsTime.text=formatDate(data.last_updated_time)
        var tUrl = data.target_url
        when {
            tUrl.contains("https://www.") -> {
                tUrl = tUrl.replace("https://www.", "")
            }
            tUrl.contains("https://") -> {
                tUrl = tUrl.replace("https://", "")
            }
            tUrl.contains("http://www") -> {
                tUrl = tUrl.replace("http://", "")
            }
        }
        tvNewsLink.text = tUrl

        var url =""
        if (!data.image_url.contains("https") && !data.image_url.contains("http")){
            url = "https://"
        }

        url += data.image_url
        mContext?.get()?.let {
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(it)
                .load(url)
                .apply(requestOptions)
                .dontAnimate()
                .placeholder(ContextCompat.getDrawable(it, R.drawable.ic_news_placeholder_large))
                .into(imageView)
        }
    }


    private fun formatDate(date:String):String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           try {


               val time = LocalDate.parse(
                   date,
                   DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:uu")
               ).atStartOfDay(
                   ZoneId.of("Asia/Kolkata")  // Or use `ZoneOffset.UTC` instead of a zone.
               )
                   .toInstant()
                   .toEpochMilli()
               val t = (System.currentTimeMillis() - time) % (60 * 60 * 1000)
               return t.toString()
           }catch (e:Exception){
               return ""
           }
      }else{
          //dosomething
          return  "2hr ago"
      }


    }

    companion object {
        private var mListener: AdapterCallBackListener? = null
        private var mContext: WeakReference<Context>? = null
        fun create(
            parent: ViewGroup,
            context: WeakReference<Context>?,
            listener: AdapterCallBackListener?
        ): NewsItemHolderLarge {
            mContext =context
            mListener =listener
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rl_item_large, parent, false)

            return NewsItemHolderLarge(view)
        }
    }
}

