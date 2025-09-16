package com.example.countrieslist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : AppCompatActivity() {
    
    private lateinit var recyclerViewCountries: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var layoutError: View
    private lateinit var tvErrorMessage: TextView
    private lateinit var btnRetry: Button
    
    private lateinit var countriesAdapter: CountriesAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initViews()
        setupRecyclerView()
        loadCountries()
    }
    
    private fun initViews() {
        recyclerViewCountries = findViewById(R.id.recyclerViewCountries)
        progressBar = findViewById(R.id.progressBar)
        layoutError = findViewById(R.id.layoutError)
        tvErrorMessage = findViewById(R.id.tvErrorMessage)
        btnRetry = findViewById(R.id.btnRetry)
        
        btnRetry.setOnClickListener {
            loadCountries()
        }
    }
    
    private fun setupRecyclerView() {
        countriesAdapter = CountriesAdapter()
        recyclerViewCountries.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = countriesAdapter
        }
    }
    
    private fun loadCountries() {
        showLoading()
        
        lifecycleScope.launch {
            try {
                val countries = loadCountriesFromAssets()
                if (countries.isNotEmpty()) {
                    countriesAdapter.updateCountries(countries)
                    showCountries()
                } else {
                    showError("No countries found")
                }
            } catch (e: Exception) {
                showError("Error loading countries: ${e.message}")
            }
        }
    }
    
    private suspend fun loadCountriesFromAssets(): List<Country> = withContext(Dispatchers.IO) {
        try {
            val json = assets.open("countries.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val listType = object : TypeToken<List<Country>>() {}.type
            gson.fromJson(json, listType)
        } catch (e: IOException) {
            emptyList()
        }
    }
    
    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerViewCountries.visibility = View.GONE
        layoutError.visibility = View.GONE
    }
    
    private fun showCountries() {
        progressBar.visibility = View.GONE
        recyclerViewCountries.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }
    
    private fun showError(message: String) {
        progressBar.visibility = View.GONE
        recyclerViewCountries.visibility = View.GONE
        layoutError.visibility = View.VISIBLE
        tvErrorMessage.text = message
    }
}
