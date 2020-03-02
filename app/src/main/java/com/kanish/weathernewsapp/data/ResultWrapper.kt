package com.kanish.weathernewsapp.data

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}