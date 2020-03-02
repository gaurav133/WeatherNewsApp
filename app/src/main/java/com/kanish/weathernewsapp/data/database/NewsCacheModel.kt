package com.kanish.weathernewsapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
@Entity(tableName = "news_cache")
data class NewsCacheModel(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo val tag: String,
    @ColumnInfo val description: String,
    @ColumnInfo val last_updated_time: String,
    @ColumnInfo val imgl: String,
    @ColumnInfo val image_url_l: String,
    @ColumnInfo val image_url: String,
    @ColumnInfo val imgsm: String,
    @ColumnInfo val imgt: String,
    @ColumnInfo val title: String,
    @ColumnInfo val target_url: String
) {

}