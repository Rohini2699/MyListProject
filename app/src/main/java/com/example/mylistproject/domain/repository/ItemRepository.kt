package com.example.mylistproject.domain.repository

import com.example.mylistproject.data.utils.Result
import com.example.mylistproject.domain.model.ApiResponse

/**
 * Repository interface for fetching items list data.
 *
 * This interface defines the contract for a repository that fetches a list of items from an API. Implementations
 * of this interface are responsible for providing the logic to retrieve the data from the appropriate data source.
 */
interface ItemRepository {

    /**
     * Fetches the list of items from the API.
     *
     * This function makes a network call to retrieve the items. It returns a [Result] object which can either be
     * a [Result.Success] containing the list of items or a [Result.Error] containing an error message.
     *
     * @return A [Result] object containing either the list of items on success or an error message on failure.
     */
    suspend fun getItems(): Result<ApiResponse>
}