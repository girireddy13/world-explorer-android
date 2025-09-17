package com.example.countrieslist

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Main activity for the World Explorer application
 * Handles nation discovery and display functionality
 */
class WorldExplorerActivity : AppCompatActivity() {
    
    // UI Components
    private lateinit var nationsRecyclerView: RecyclerView
    private lateinit var discoveryProgressBar: CircularProgressIndicator
    private lateinit var explorationErrorLayout: LinearLayout
    private lateinit var errorMessageText: TextView
    private lateinit var exploreAgainButton: MaterialButton
    
    // Data Components
    private lateinit var nationsExplorationAdapter: NationsExplorationAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_world_explorer)
        
        initializeUserInterface()
        setupNationsExplorationView()
        startWorldDiscovery()
    }
    
    /**
     * Initializes all UI components and sets up event handlers
     */
    private fun initializeUserInterface() {
        nationsRecyclerView = findViewById(R.id.nationsRecyclerView)
        discoveryProgressBar = findViewById(R.id.discoveryProgressBar)
        explorationErrorLayout = findViewById(R.id.explorationErrorLayout)
        errorMessageText = findViewById(R.id.errorMessageText)
        exploreAgainButton = findViewById(R.id.exploreAgainButton)
        
        exploreAgainButton.setOnClickListener {
            startWorldDiscovery()
        }
    }
    
    /**
     * Sets up the RecyclerView for displaying nations
     */
    private fun setupNationsExplorationView() {
        nationsExplorationAdapter = NationsExplorationAdapter()
        nationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@WorldExplorerActivity)
            adapter = nationsExplorationAdapter
            // Add smooth scrolling animation
            itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        }
    }
    
    /**
     * Starts the world discovery process
     */
    private fun startWorldDiscovery() {
        displayDiscoveryInProgress()
        
        lifecycleScope.launch {
            try {
                // Add a small delay to show loading state
                delay(1500)
                
                val discoveredNations = loadNationsFromLocalExplorationData()
                if (discoveredNations.isNotEmpty()) {
                    handleSuccessfulDiscovery(discoveredNations)
                } else {
                    handleDiscoveryFailure("No nations found during exploration")
                }
            } catch (exception: Exception) {
                handleDiscoveryFailure("Exploration failed: ${exception.message}")
            }
        }
    }
    
    /**
     * Loads nation data from local exploration assets
     */
    private suspend fun loadNationsFromLocalExplorationData(): List<NationModel> = withContext(Dispatchers.IO) {
        try {
            val explorationDataJson = assets.open("nations_exploration_data.json")
                .bufferedReader()
                .use { it.readText() }
            
            val gson = Gson()
            val nationsListType = object : TypeToken<List<NationModel>>() {}.type
            gson.fromJson(explorationDataJson, nationsListType) ?: emptyList()
        } catch (ioException: IOException) {
            emptyList()
        }
    }
    
    /**
     * Displays loading state during discovery
     */
    private fun displayDiscoveryInProgress() {
        discoveryProgressBar.visibility = View.VISIBLE
        nationsRecyclerView.visibility = View.GONE
        explorationErrorLayout.visibility = View.GONE
    }
    
    /**
     * Handles successful nation discovery
     */
    private fun handleSuccessfulDiscovery(nations: List<NationModel>) {
        nationsExplorationAdapter.refreshDiscoveredNations(nations)
        discoveryProgressBar.visibility = View.GONE
        nationsRecyclerView.visibility = View.VISIBLE
        explorationErrorLayout.visibility = View.GONE
    }
    
    /**
     * Handles discovery failure with error message
     */
    private fun handleDiscoveryFailure(errorMessage: String) {
        discoveryProgressBar.visibility = View.GONE
        nationsRecyclerView.visibility = View.GONE
        explorationErrorLayout.visibility = View.VISIBLE
        errorMessageText.text = errorMessage
    }
    
    /**
     * Gets the current exploration statistics
     */
    private fun getExplorationStatistics(): String {
        val nationCount = nationsExplorationAdapter.getCurrentNationsList().size
        return "Discovered $nationCount nations"
    }
}
