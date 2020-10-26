package com.example.covid_19monitorapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19monitorapp.data.LookUpData
import com.example.covid_19monitorapp.R
import com.example.covid_19monitorapp.viewHolder.LookUpViewHolder

class LookUpAdapter(private val lookUpList: MutableList<LookUpData>) : RecyclerView.Adapter<LookUpViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookUpViewHolder {
        return  LookUpViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_lookup, parent,false))
    }

    override fun onBindViewHolder(holder: LookUpViewHolder, position: Int) {
        holder.bind(lookUpList[position])
    }

    override fun getItemCount(): Int {
        return lookUpList.size
    }

    fun filterAndUpdateData(keyword: String) {
         val newList = lookUpList.filter { it.provincename.contains(keyword as CharSequence, true) }
        updateData(newList)
    }

    fun updateData(newList: List<LookUpData>) {
        lookUpList.clear()
        lookUpList.addAll(newList)

        notifyDataSetChanged()
    }
}