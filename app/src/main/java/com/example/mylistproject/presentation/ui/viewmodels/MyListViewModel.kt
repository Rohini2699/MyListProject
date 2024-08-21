package com.example.mylistproject.presentation.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylistproject.domain.model.Item
import com.example.mylistproject.data.utils.Result
import com.example.mylistproject.domain.usecase.GetItemsGroupedByListIdUseCase
import com.example.mylistproject.domain.repository.ItemRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * ViewModel class responsible for managing data related to the list of items.
 *
 * The [MyListViewModel] class interacts with the repository and use case to fetch and manage the data
 * related to items. It uses LiveData to expose the data and handles any errors that occur during the data fetching process.
 *
 * @param itemRepository The repository responsible for providing access to item data.
 * @param getItemsGroupedByListIdUseCase The use case for getting items grouped by list ID.
 */
class MyListViewModel(
    private val itemRepository: ItemRepository,
    private val getItemsGroupedByListIdUseCase: GetItemsGroupedByListIdUseCase
) : ViewModel() {

    val groupedItems = MutableLiveData<Map<String, List<Item>>>()
    val errorMessage = MutableLiveData<String>()
    val isShowProgress = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled : ${throwable.localizedMessage}")
    }

    /**
     * Fetches the grouped items data from the use case and updates LiveData accordingly.
     *
     * This method launches a coroutine to fetch the grouped items using the [getItemsGroupedByListIdUseCase].
     * It updates the [groupedItems] LiveData with the fetched data on success, or updates the [errorMessage] LiveData
     * with an error message on failure. It also manages the [isShowProgress] LiveData to indicate the loading state.
     */
    fun getGroupedItems() {
        isShowProgress.value = true
        viewModelScope.launch(exceptionHandler) {
            val result = getItemsGroupedByListIdUseCase.execute()
            if (result is Result.Success) {
                groupedItems.postValue(result.data)
            } else if (result is Result.Error) {
                onError("Error: ${result.message}")
            }
            isShowProgress.postValue(false)
        }
    }

    /**
     * Handles errors by updating the [errorMessage] LiveData.
     *
     * @param message The error message to be displayed.
     */
    private fun onError(message: String) {
        errorMessage.value = message
        isShowProgress.value = false
    }

    /**
     * Cancels all coroutines when the ViewModel is cleared to avoid memory leaks.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }
}
