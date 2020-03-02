package com.kanish.weathernewsapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kanish.weathernewsapp.model.NewsItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Kanish Roshan on 2020-02-27.
 */
@Dao
public interface NewsDao {
    @Query("SELECT * FROM news_cache")
    fun getCacheNewsList(): List<NewsItem>




    @Insert
    fun insertAll(news: List<NewsItem>)
//
   @Query("DELETE FROM news_cache ")
    fun deleteAll()

}
