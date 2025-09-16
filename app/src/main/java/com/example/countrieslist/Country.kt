package com.example.countrieslist

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val name: Name,
    
    @SerializedName("region")
    val region: String,
    
    @SerializedName("cca2")
    val code: String,
    
    @SerializedName("capital")
    val capital: List<String>?
) {
    data class Name(
        @SerializedName("common")
        val common: String,
        @SerializedName("official")
        val official: String
    )
}
