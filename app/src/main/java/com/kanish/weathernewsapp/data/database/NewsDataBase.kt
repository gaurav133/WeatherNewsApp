package com.kanish.weathernewsapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kanish.weathernewsapp.model.NewsItem

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
@Database(entities = [NewsItem::class], version = 1)
 abstract class NewsDataBase: RoomDatabase() {
    var TEST_MODE = false

    private val DB_NAME = "news_cache.db"

    @Volatile
    private var apiCacheDao: NewsDao? = null

    companion object {

        @Volatile
        private var INSTANCE: NewsDataBase? = null

        fun getDbInstance(context: Context): NewsDataBase {
            val tempInstance = INSTANCE



            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDataBase::class.java, "budget_table"
                ).build()

                INSTANCE = instance
                return instance
            }

        }
    }


    private fun create(context: Context): NewsDataBase? {
        return Room.databaseBuilder(
            context,
            NewsDataBase::class.java, DB_NAME
        ).build()
    }

    abstract fun getApiCacheDao(): NewsDao?
}