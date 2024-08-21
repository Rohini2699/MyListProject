package com.example.mylistproject.domain.usecase

import com.example.mylistproject.domain.model.Item
import com.example.mylistproject.data.utils.Result
import com.example.mylistproject.domain.repository.ItemRepository

/**
 * Use case implementation responsible for fetching items from the repository and grouping them by listId.
 *
 * This class implements the [GetItemsGroupedByListIdUseCase] interface and defines the logic for fetching
 * items from the provided repository and grouping them by their listId.
 *
 * @param itemRepository The repository responsible for providing access to item data.
 */
class GetItemsGroupedByListIdUseCaseImpl(private val itemRepository: ItemRepository) : GetItemsGroupedByListIdUseCase {

    /**
     * Executes the use case to fetch items and group them by listId.
     *
     * This method fetches the list of items from the repository, checks for malformed data, sorts the items,
     * and groups them by their listId. It returns a [Result] object that either contains a map of grouped items
     * on success or an error message on failure.
     *
     * @return A [Result] object containing either a map of grouped items on success or an error message on failure.
     */
    override suspend fun execute(): Result<Map<String, List<Item>>> {
        val result = itemRepository.getItems()
        return if (result is Result.Success) {
            val employees = result.data.employees
            if (employees.any { it.isMalformed() }) {
                Result.Error("Malformed employee data found")
            } else {
                val sortedItems = employees.filter { it.full_name != null }.sortedBy { it.full_name }
                val groupedItems = sortedItems.groupBy { it.full_name!! }
                Result.Success(groupedItems)
            }
        } else {
            result as Result.Error
        }
    }
}
