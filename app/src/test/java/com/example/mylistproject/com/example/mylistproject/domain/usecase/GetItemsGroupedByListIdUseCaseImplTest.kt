package com.example.mylistproject.domain.usecase

import com.example.mylistproject.domain.model.ApiResponse
import com.example.mylistproject.domain.model.Item
import com.example.mylistproject.data.utils.Result
import com.example.mylistproject.domain.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetItemsGroupedByListIdUseCaseImplTest {

    private lateinit var itemRepository: ItemRepository
    private lateinit var useCase: GetItemsGroupedByListIdUseCaseImpl

    @Before
    fun setUp() {
        itemRepository = mockk()
        useCase = GetItemsGroupedByListIdUseCaseImpl(itemRepository)
    }

    @Test
    fun `execute returns grouped items on success`() = runBlocking {
        // Arrange
        val items = arrayListOf(
            Item("1", "John Doe", "john@example.com", "1234567890", "Biography", "url1", "url2", "Team A", "Full-time"),
            Item("2", "Jane Doe", "jane@example.com", "0987654321", "Biography", "url3", "url4", "Team B", "Part-time")
        )
        val apiResponse = ApiResponse(items)
        val result = Result.Success(apiResponse)
        coEvery { itemRepository.getItems() } returns result

        // Act
        val response = useCase.execute()

        // Assert
        val expectedGroupedItems = mapOf(
            "John Doe" to listOf(items[0]),
            "Jane Doe" to listOf(items[1])
        )
        assertEquals(Result.Success(expectedGroupedItems), response)
    }

    @Test
    fun `execute returns error on repository error`() = runBlocking {
        // Arrange
        val errorMessage = "Error fetching items"
        coEvery { itemRepository.getItems() } returns Result.Error(errorMessage)

        // Act
        val response = useCase.execute()

        // Assert
        assertEquals(Result.Error(errorMessage), response)
    }

    @Test
    fun `execute returns error on malformed data`() = runBlocking {
        // Arrange
        val items = arrayListOf(
            Item("1", null, "john@example.com", "1234567890", "Biography", "url1", "url2", "Team A", "Full-time")
        )
        val apiResponse = ApiResponse(items)
        val result = Result.Success(apiResponse)
        coEvery { itemRepository.getItems() } returns result

        // Act
        val response = useCase.execute()

        // Assert
        assertEquals(Result.Error("Malformed employee data found"), response)
    }
}
