package com.example.countrieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountriesAdapter : RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {
    
    private var countries: List<Country> = emptyList()
    
    fun updateCountries(newCountries: List<Country>) {
        countries = newCountries
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }
    
    override fun getItemCount(): Int = countries.size
    
    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCountryName: TextView = itemView.findViewById(R.id.tvCountryName)
        private val tvRegion: TextView = itemView.findViewById(R.id.tvRegion)
        private val tvCapital: TextView = itemView.findViewById(R.id.tvCapital)
        private val tvCountryCode: TextView = itemView.findViewById(R.id.tvCountryCode)
        
        fun bind(country: Country) {
            tvCountryName.text = country.name.common
            tvRegion.text = country.region
            tvCapital.text = country.capital?.firstOrNull() ?: "N/A"
            tvCountryCode.text = country.code
        }
    }
}
