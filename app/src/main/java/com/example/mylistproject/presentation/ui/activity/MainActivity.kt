package com.example.mylistproject.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylistproject.data.networkmodule.RetrofitInstanceModule
import com.example.mylistproject.data.repository.ItemRepositoryImpl
import com.example.mylistproject.databinding.ActivityMainBinding
import com.example.mylistproject.domain.model.Item
import com.example.mylistproject.domain.usecase.GetItemsGroupedByListIdUseCaseImpl
import com.example.mylistproject.presentation.ui.adapter.ItemRecyclerAdapter
import com.example.mylistproject.presentation.ui.factories.ItemViewModelFactory
import com.example.mylistproject.presentation.ui.viewmodels.MyListViewModel

/**
 * MainActivity is the entry point of the application, responsible for setting up the UI and managing the ViewModel.
 * It initializes the RecyclerView, ViewModel, observes LiveData, and handles user interactions.
 */
class MainActivity : AppCompatActivity() {
    internal lateinit var activityMainBinding: ActivityMainBinding
    internal lateinit var itemAdapter: ItemRecyclerAdapter
    internal lateinit var myListViewModel: MyListViewModel

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this contains the data it most recently supplied in onSaveInstanceState.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setUpRecyclerView()
        setUpViewModel()
        observeViewModel()
        setUpSwipeToRefresh()
    }

    /**
     * Sets up the RecyclerView with the ItemRecyclerAdapter and a LinearLayoutManager.
     */
    internal fun setUpRecyclerView() {
        itemAdapter = ItemRecyclerAdapter()
        activityMainBinding.itemRecyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    /**
     * Initializes the ViewModel by creating an instance using the ItemViewModelFactory and sets up initial data fetching.
     */
    internal fun setUpViewModel() {
        val apiService = RetrofitInstanceModule().theRetrofitInstance()
        val repository = ItemRepositoryImpl(apiService)
        val getItemsGroupedByListIdUseCase = GetItemsGroupedByListIdUseCaseImpl(repository)
        val viewModelFactory = ItemViewModelFactory(repository, getItemsGroupedByListIdUseCase)
        myListViewModel = ViewModelProvider(this, viewModelFactory).get(MyListViewModel::class.java)
        myListViewModel.getGroupedItems()
    }

    /**
     * Observes the LiveData objects in the ViewModel to update the UI based on changes in the data.
     */
    internal fun observeViewModel() {
        myListViewModel.groupedItems.observe(this, Observer { groupedItems ->
            if (groupedItems != null && groupedItems.isNotEmpty()) {
                val flattenedList = mutableListOf<Item>()
                groupedItems.values.forEach { list ->
                    flattenedList.addAll(list)
                }
                itemAdapter.setData(flattenedList)
                if (flattenedList.isEmpty()) {
                    activityMainBinding.textEmptyListHolder.visibility = View.VISIBLE
                    Toast.makeText(this, "No items found!", Toast.LENGTH_SHORT).show()
                } else {
                    activityMainBinding.textEmptyListHolder.visibility = View.GONE
                }
            } else {
                activityMainBinding.textEmptyListHolder.visibility = View.VISIBLE
                Toast.makeText(this, "No items found!", Toast.LENGTH_SHORT).show()
            }
            activityMainBinding.swipeRefreshLayout.isRefreshing = false
        })

        myListViewModel.isShowProgress.observe(this, Observer { showProgress ->
            activityMainBinding.mainProgressBar.visibility = if (showProgress) View.VISIBLE else View.GONE
        })

        myListViewModel.errorMessage.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            activityMainBinding.textEmptyListHolder.visibility = View.VISIBLE
            activityMainBinding.textEmptyListHolder.text = errorMessage
        })
    }

    /**
     * Sets up the swipe-to-refresh functionality to reload the data when the user performs a swipe gesture.
     */
    internal fun setUpSwipeToRefresh() {
        activityMainBinding.swipeRefreshLayout.setOnRefreshListener {
            myListViewModel.getGroupedItems()
        }
    }
}
