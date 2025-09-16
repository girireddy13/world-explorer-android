package com.example.countrieslist

import org.junit.Test
import org.junit.Assert.*

class SimpleTest {
    
    @Test
    fun `should create country object`() {
        // Given
        val country = Country("USA", "NA", "US", "Washington")
        
        // Then
        assertEquals("USA", country.name)
        assertEquals("NA", country.region)
        assertEquals("US", country.code)
        assertEquals("Washington", country.capital)
    }
    
    @Test
    fun `should create adapter`() {
        // Given & When
        val adapter = CountriesAdapter()
        
        // Then
        assertNotNull(adapter)
        assertEquals(0, adapter.itemCount)
    }
}
