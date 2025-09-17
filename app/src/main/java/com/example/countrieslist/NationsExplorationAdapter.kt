package com.example.countrieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView adapter for displaying nations in the exploration list
 */
class NationsExplorationAdapter : RecyclerView.Adapter<NationsExplorationAdapter.NationExplorerViewHolder>() {
    
    private var discoveredNations: List<NationModel> = emptyList()
    
    /**
     * Updates the list of discovered nations
     */
    fun refreshDiscoveredNations(newNations: List<NationModel>) {
        discoveredNations = newNations.sortedBy { it.getDisplayName() }
        notifyDataSetChanged()
    }
    
    /**
     * Gets the currently displayed nations
     */
    fun getCurrentNationsList(): List<NationModel> = discoveredNations
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NationExplorerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nation_card, parent, false)
        return NationExplorerViewHolder(itemView)
    }
    
    override fun onBindViewHolder(holder: NationExplorerViewHolder, position: Int) {
        holder.bindNationData(discoveredNations[position])
    }
    
    override fun getItemCount(): Int = discoveredNations.size
    
    /**
     * ViewHolder class for individual nation items
     */
    class NationExplorerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        
        private val nationNameDisplay: TextView = itemView.findViewById(R.id.nationNameText)
        private val continentRegionDisplay: TextView = itemView.findViewById(R.id.continentRegionText)
        private val capitalCityDisplay: TextView = itemView.findViewById(R.id.capitalCityText)
        private val nationCodeDisplay: TextView = itemView.findViewById(R.id.nationCodeBadge)
        
        /**
         * Binds nation data to the view elements
         */
        fun bindNationData(nation: NationModel) {
            nationNameDisplay.text = nation.getDisplayName()
            continentRegionDisplay.text = nation.continentRegion
            capitalCityDisplay.text = nation.getPrimaryCapital()
            nationCodeDisplay.text = nation.nationCode
        }
    }
}
