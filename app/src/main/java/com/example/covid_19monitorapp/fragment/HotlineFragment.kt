package com.example.covid_19monitorapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid_19monitorapp.adapter.HotlineAdapter
import com.example.covid_19monitorapp.data.HotlineData
import com.example.covid_19monitorapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.dialog_hotline.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class HotlineFragment : BottomSheetDialogFragment() {

    private val okHttpClient = OkHttpClient()
    val request =
        Request.Builder().url("https://bncc-corona-versus.firebaseio.com/v1/hotlines.json")
            .build()

    private val mockHotlineList = mutableListOf(
        HotlineData(name = "Loading..", imgIcon = "", phone = "Loading..")
    )

    private val hotlineAdapter = HotlineAdapter(mockHotlineList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_hotline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hotlineClose.setOnClickListener() {
            dismiss()
        }

        rvHotline.layoutManager = LinearLayoutManager(activity)
        rvHotline.adapter = hotlineAdapter

        reqHotlineData(request)
    }

    private fun reqHotlineData(request: Request) {
        okHttpClient
            .newCall(request)
            .enqueue(getCallback(hotlineAdapter))
    }

    private fun bindData(jsonObject: JSONObject): HotlineData {
        return HotlineData(
            imgIcon = jsonObject.getString("img_icon"),
            name = jsonObject.getString("name"),
            phone = jsonObject.getString("phone")
        )
    }

    private fun getCallback(hotlineAdapter: HotlineAdapter): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val hotlineListFromNetwork = mutableListOf<HotlineData>()

                    activity?.runOnUiThread {
                        for (i in 0 until jsonArray.length()) {
                            hotlineListFromNetwork.add(bindData(jsonArray.getJSONObject(i)))
                        }
                        hotlineAdapter.updateData(hotlineListFromNetwork)
                    }

                } catch (e: Exception) {
                    activity?.runOnUiThread {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}