package com.example.countrieslist

import org.junit.Test
import org.junit.Assert.*

class CountryTest {
    
    @Test
    fun `Country data class should create valid instance`() {
        // Given
        val name = "United States of America"
        val region = "NA"
        val code = "US"
        val capital = "Washington, D.C."
        
        // When
        val country = Country(name, region, code, capital)
        
        // Then
        assertEquals(name, country.name)
        assertEquals(region, country.region)
        assertEquals(code, country.code)
        assertEquals(capital, country.capital)
    }
    
    @Test
    fun `Country should handle empty strings`() {
        // Given
        val emptyString = ""
        
        // When
        val country = Country(emptyString, emptyString, emptyString, emptyString)
        
        // Then
        assertEquals(emptyString, country.name)
        assertEquals(emptyString, country.region)
        assertEquals(emptyString, country.code)
        assertEquals(emptyString, country.capital)
    }
    
    @Test
    fun `Country should handle special characters`() {
        // Given
        val nameWithSpecialChars = "Côte d'Ivoire"
        val regionWithSpecialChars = "AF"
        val codeWithSpecialChars = "CI"
        val capitalWithSpecialChars = "Yamoussoukro"
        
        // When
        val country = Country(nameWithSpecialChars, regionWithSpecialChars, codeWithSpecialChars, capitalWithSpecialChars)
        
        // Then
        assertEquals(nameWithSpecialChars, country.name)
        assertEquals(regionWithSpecialChars, country.region)
        assertEquals(codeWithSpecialChars, country.code)
        assertEquals(capitalWithSpecialChars, country.capital)
    }
}
