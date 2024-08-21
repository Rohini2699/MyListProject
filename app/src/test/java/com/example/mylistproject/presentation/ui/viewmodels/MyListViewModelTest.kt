package com.example.mylistproject.presentation.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mylistproject.domain.model.Item
import com.example.mylistproject.data.utils.Result
import com.example.mylistproject.domain.usecase.GetItemsGroupedByListIdUseCase
import com.example.mylistproject.domain.repository.ItemRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MyListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @MockK
    private lateinit var itemRepository: ItemRepository

    @MockK
    private lateinit var getItemsGroupedByListIdUseCase: GetItemsGroupedByListIdUseCase

    @MockK
    private lateinit var observer: Observer<Map<String, List<Item>>>

    @MockK
    private lateinit var errorObserver: Observer<String>

    @MockK
    private lateinit var progressObserver: Observer<Boolean>

    private lateinit var viewModel: MyListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = MyListViewModel(itemRepository, getItemsGroupedByListIdUseCase)
        viewModel.groupedItems.observeForever(observer)
        viewModel.errorMessage.observeForever(errorObserver)
        viewModel.isShowProgress.observeForever(progressObserver)

        // Setup default behavior for observers
        coEvery { observer.onChanged(any()) } just runs
        coEvery { errorObserver.onChanged(any()) } just runs
        coEvery { progressObserver.onChanged(any()) } just runs
    }

    @Test
    fun `getGroupedItems returns success`() = runBlocking {
        // Arrange
        val items = listOf(
            Item("1", "John Doe", "john@example.com", "1234567890", "Biography", "url1", "url2", "Team A", "Full-time")
        )
        val groupedItems = mapOf("John Doe" to items)
        coEvery { getItemsGroupedByListIdUseCase.execute() } returns Result.Success(groupedItems)

        // Act
        viewModel.getGroupedItems()

        // Assert
        verify { progressObserver.onChanged(true) }
        verify { observer.onChanged(groupedItems) }
        verify { progressObserver.onChanged(false) }
    }

    @Test
    fun `getGroupedItems returns error`() = runBlocking {
        // Arrange
        val errorMessage = "Error fetching items"
        coEvery { getItemsGroupedByListIdUseCase.execute() } returns Result.Error(errorMessage)

        // Act
        viewModel.getGroupedItems()

        // Assert
        verify { progressObserver.onChanged(true) }
        verify { errorObserver.onChanged("Error: $errorMessage") }
        verify { progressObserver.onChanged(false) }
    }

    @Test
    fun `progress indicator is shown during data fetch`() = runBlocking {
        // Arrange
        coEvery { getItemsGroupedByListIdUseCase.execute() } returns Result.Success(emptyMap())

        // Act
        viewModel.getGroupedItems()

        // Assert
        verify { progressObserver.onChanged(true) }
        verify { progressObserver.onChanged(false) }
    }
}
