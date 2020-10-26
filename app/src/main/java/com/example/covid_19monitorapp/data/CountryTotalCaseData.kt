package com.example.covid_19monitorapp.data

import com.google.gson.annotations.SerializedName

data class CountryTotalCaseData (
    @SerializedName("name")
    var country: String,

    @SerializedName("positif")
    var totalCase: String,

    @SerializedName("dirawat")
    var totatlPositif: String,

    @SerializedName("sembuh")
    var totalRecovered: String,

    @SerializedName("meninggal")
    var totalDead: String
)
