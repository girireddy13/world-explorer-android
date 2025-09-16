package com.example.countrieslist

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking

class CountriesApiServiceTest {
    
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: CountriesApiService
    
    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        apiService = retrofit.create(CountriesApiService::class.java)
    }
    
    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
    
    @Test
    fun `should return countries list when API call is successful`() {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                [
                    {
                        "name": "Afghanistan",
                        "region": "AS",
                        "code": "AF",
                        "capital": "Kabul"
                    },
                    {
                        "name": "Albania",
                        "region": "EU",
                        "code": "AL",
                        "capital": "Tirana"
                    }
                ]
            """.trimIndent())
        
        mockWebServer.enqueue(mockResponse)
        
        // When
        val countries = runBlocking { apiService.getCountries() }
        
        // Then
        assertEquals(2, countries.size)
        assertEquals("Afghanistan", countries[0].name)
        assertEquals("AS", countries[0].region)
        assertEquals("AF", countries[0].code)
        assertEquals("Kabul", countries[0].capital)
    }
    
    @Test
    fun `should return empty list when API returns empty array`() {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("[]")
        
        mockWebServer.enqueue(mockResponse)
        
        // When
        val countries = runBlocking { apiService.getCountries() }
        
        // Then
        assertTrue(countries.isEmpty())
    }
    
    @Test
    fun `should handle API error response`() {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(404)
            .setBody("Not Found")
        
        mockWebServer.enqueue(mockResponse)
        
        // When & Then
        try {
            runBlocking { apiService.getCountries() }
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertTrue(e.message?.contains("404") == true || e.message?.contains("Not Found") == true)
        }
    }
    
    @Test
    fun `should handle malformed JSON response`() {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("invalid json")
        
        mockWebServer.enqueue(mockResponse)
        
        // When & Then
        try {
            runBlocking { apiService.getCountries() }
            fail("Expected exception to be thrown")
        } catch (e: Exception) {
            assertTrue(e.message?.contains("JSON") == true || e.message?.contains("parse") == true)
        }
    }
}