package com.kanish.weathernewsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
/**
 * Created by Kanish Roshan on 2020-03-01.
 */
@Entity(tableName = "news_cache")
data class NewsItem(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @SerializedName("dc")  val tag: String,
    @SerializedName("desc") val description: String,
    @SerializedName("etime")val last_updated_time: String,
    @SerializedName("imgl") val image_url_l: String,
    @SerializedName("imgr")  val image_url: String,
    val imgsm: String,
    val imgt: String,
    @SerializedName("t")   val title: String,
    @SerializedName("u")    val target_url: String
)