package com.example.countrieslist

import retrofit2.http.GET

/**
 * API service interface for world exploration data
 */
interface WorldExplorationService {
    @GET("v3.1/all")
    suspend fun discoverAllNations(): List<NationModel>
}

/**
 * Service for exploring nations data
 */
object NationDiscoveryClient {
    private const val EXPLORATION_BASE_URL = "https://restcountries.com/"
    
    // For this implementation, we'll use local data
    // In a real app, this would be configured with Retrofit
    
    /**
     * Discovers nations from local data source
     */
    suspend fun exploreNationsFromLocalSource(): List<NationModel> {
        // This will be implemented in the activity
        return emptyList()
    }
}
