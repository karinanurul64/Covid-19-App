package com.example.covid_19monitorapp.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19monitorapp.data.LookUpData
import kotlinx.android.synthetic.main.item_lookup.view.*

class LookUpViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
    fun bind(data: LookUpData){
        itemView.headerList.text = data.provincename
        itemView.dataRecovery.text = "${data.numberOfRecoveredCase}"
        itemView.dataPositif.text = "${data.numberOfPositiveCase}"
        itemView.dataDead.text = "${data.numberOfDeathCase}"
    }
}