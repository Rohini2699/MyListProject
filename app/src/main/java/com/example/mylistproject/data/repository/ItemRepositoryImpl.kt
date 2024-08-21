package com.example.mylistproject.data.repository

import com.example.mylistproject.data.service.APIService
import com.example.mylistproject.domain.repository.ItemRepository
import retrofit2.HttpException
import java.io.IOException
import com.example.mylistproject.data.utils.Result
import com.example.mylistproject.domain.model.ApiResponse

/**
 * Implementation of the [ItemRepository] interface that fetches items from an API service.
 *
 * @param apiService The API service used to fetch items.
 */
class ItemRepositoryImpl(private val apiService: APIService) : ItemRepository {

    /**
     * Fetches the list of items from the API.
     *
     * This function makes a network call to the API service to retrieve the items. It returns a [Result] object
     * which can either be a [Result.Success] containing the list of items or a [Result.Error] containing an error message.
     *
     * @return A [Result] object containing either the list of items on success or an error message on failure.
     */
    override suspend fun getItems(): Result<ApiResponse> {
        return try {
            val response = apiService.getItems()
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Result.Success(responseBody)
                } else {
                    Result.Error("Response body is null")
                }
            } else {
                Result.Error("HTTP Error: ${response.code()}")
            }
        } catch (e: IOException) {
            Result.Error("Network Error: ${e.message}")
        } catch (e: HttpException) {
            Result.Error("HTTP Error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Error: ${e.message}")
        }
    }
}