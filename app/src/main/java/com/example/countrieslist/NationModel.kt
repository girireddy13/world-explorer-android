package com.example.countrieslist

import com.google.gson.annotations.SerializedName

/**
 * Data model representing a nation with its geographical and political information
 */
data class NationModel(
    @SerializedName("name")
    val nationName: NationName,
    
    @SerializedName("region")
    val continentRegion: String,
    
    @SerializedName("cca2")
    val nationCode: String,
    
    @SerializedName("capital")
    val capitalCity: List<String>?
) {
    /**
     * Nested data class for nation name information
     */
    data class NationName(
        @SerializedName("common")
        val commonName: String,
        @SerializedName("official")
        val officialName: String
    )
    
    /**
     * Extension function to get the primary capital city
     */
    fun getPrimaryCapital(): String {
        return capitalCity?.firstOrNull() ?: "Not Available"
    }
    
    /**
     * Extension function to get display name
     */
    fun getDisplayName(): String {
        return nationName.commonName
    }
}
