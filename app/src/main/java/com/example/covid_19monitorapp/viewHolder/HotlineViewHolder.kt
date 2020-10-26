package com.example.covid_19monitorapp.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.covid_19monitorapp.data.HotlineData
import com.example.covid_19monitorapp.activity.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_hotline.view.*
import kotlinx.android.synthetic.main.item_hotline.view.hotlineHeader

class HotlineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(data: HotlineData){
        if(data.imgIcon.isNotBlank()){
            Picasso.get().load(data.imgIcon).into(itemView.ivIconHotline)
        }
        itemView.hotlineHeader.text = data.name
        itemView.hotlineNumber.text = data.phone

        val context = itemView.context
        val mainActivity = MainActivity()

        itemView.phoneNumber.setOnClickListener {
            mainActivity.phoneCall(context, data.phone)
        }

    }

}