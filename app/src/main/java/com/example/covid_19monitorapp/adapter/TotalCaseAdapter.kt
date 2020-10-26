package com.example.covid_19monitorapp.adapter

import com.example.covid_19monitorapp.data.CountryTotalCaseData

class TotalCaseAdapter(private val countryTotalCaseDataList: MutableList<CountryTotalCaseData>) {

    fun updateData(newList: List<CountryTotalCaseData>) {
        countryTotalCaseDataList.clear()
        countryTotalCaseDataList.addAll(newList)
    }

}