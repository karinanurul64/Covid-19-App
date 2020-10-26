package com.example.covid_19monitorapp.presenter

import android.util.Log
import com.example.covid_19monitorapp.contract.HomeContract
import com.example.covid_19monitorapp.data.CountryTotalCaseData
import com.example.covid_19monitorapp.network.HomeRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(private val view: HomeContract.View): HomeContract.Presenter {
    val homeRetrofitService = HomeRetrofitService.createHomeService()

    override fun reqTotalCountryCaseData() {
        homeRetrofitService.getTotalCase().enqueue(object : Callback<List<CountryTotalCaseData>> {
            override fun onResponse(
                call: Call<List<CountryTotalCaseData>>,
                response: Response<List<CountryTotalCaseData>>
            ) {
                view.bindData(response.body()!![0])
            }

            override fun onFailure(call: Call<List<CountryTotalCaseData>>, t: Throwable) {
                Log.e("TotalCountryDataReq", t.message.toString())
            }
        })
    }
}