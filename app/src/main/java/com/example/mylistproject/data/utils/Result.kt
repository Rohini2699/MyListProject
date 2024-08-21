package com.example.mylistproject.data.utils

/**
 * A sealed class representing the result of an operation.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}