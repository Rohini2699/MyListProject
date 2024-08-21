package com.example.mylistproject.presentation.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mylistproject.domain.usecase.GetItemsGroupedByListIdUseCase
import com.example.mylistproject.presentation.ui.viewmodels.MyListViewModel
import com.example.mylistproject.domain.repository.ItemRepository

/**
 * Factory class responsible for creating instances of [MyListViewModel].
 *
 * This factory class provides the necessary dependencies to create an instance of [MyListViewModel], including
 * an item repository and a use case for grouping items by listId.
 *
 * @param itemRepository The repository for fetching item data.
 * @param getItemsGroupedByListIdUseCase The use case for grouping items by listId.
 */
class ItemViewModelFactory(
    private val itemRepository: ItemRepository,
    private val getItemsGroupedByListIdUseCase: GetItemsGroupedByListIdUseCase
) : ViewModelProvider.Factory {

    /**
     * Creates an instance of the specified ViewModel class.
     *
     * This method creates and returns an instance of the specified ViewModel class. It ensures that the created
     * ViewModel is of type [MyListViewModel] and provides it with the required dependencies.
     *
     * @param modelClass The class of the ViewModel to create.
     * @return An instance of the specified ViewModel.
     * @throws IllegalArgumentException if the ViewModel class is unknown.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyListViewModel::class.java)) {
            return MyListViewModel(itemRepository, getItemsGroupedByListIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
