package com.example.covid_19monitorapp.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid_19monitorapp.adapter.LookUpAdapter
import com.example.covid_19monitorapp.data.LookUpData
import com.example.covid_19monitorapp.R
import kotlinx.android.synthetic.main.activity_lookup.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class LookupActivity : AppCompatActivity() {

    private val listOfLookUpData = mutableListOf<LookUpData>(
        LookUpData("Loading...", 100, 100, 100)
    )
    private val lookUpAdapter = LookUpAdapter(listOfLookUpData)
    private val okHttpClient = OkHttpClient()
    private var request =
        Request.Builder().url("https://api.kawalcorona.com/indonesia/provinsi/#").build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lookup)
        title = "Lookup Activity"

        rvLookUp.layoutManager = LinearLayoutManager(this)
        rvLookUp.adapter = lookUpAdapter

        reqLookUpData(request)

        setViewEventListener()
    }

    private fun setViewEventListener() {
        backButton.setOnClickListener {
            onBackPressed()
        }

        ibDelInputButton.setOnClickListener {
            if (!etSearchInput.text.isEmpty()) {
                etSearchInput.text.clear()
            }
        }

        etSearchInput.addTextChangedListener(searchTextChangeListener(request))
    }

    private fun searchTextChangeListener(request: Request): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.i("Search", "Before Text Changed")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.i("Search", "On Text Changed")
            }

            override fun afterTextChanged(p0: Editable?) {
                this@LookupActivity.runOnUiThread {
                    if (!p0.toString().isNullOrBlank()) {
                        lookUpAdapter.filterAndUpdateData(p0.toString())
                    } else {
                        reqLookUpData(request)
                    }
                }
            }
        }
    }

    private fun reqLookUpData(request: Request) {
        okHttpClient
            .newCall(request)
            .enqueue(getCallback(lookUpAdapter))
    }

    private fun bindData(jsonObject: JSONObject): LookUpData {
        return LookUpData(
            jsonObject.getString("Provinsi"),
            jsonObject.getInt("Kasus_Posi"),
            jsonObject.getInt("Kasus_Semb"),
            jsonObject.getInt("Kasus_Meni")
        )
    }

    private fun getCallback(lookUpAdapter: LookUpAdapter): Callback {
        return object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@LookupActivity.runOnUiThread {
                    Toast.makeText(this@LookupActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonString = response.body?.string()
                    val jsonArray = JSONArray(jsonString)
                    val lookUpListFromNetwork = mutableListOf<LookUpData>()

                    for (i in 0 until jsonArray.length()) {
                        lookUpListFromNetwork.add(
                            bindData(
                                jsonArray.getJSONObject(i).getJSONObject("attributes")
                            )
                        )
                    }
                    this@LookupActivity.runOnUiThread {
                        lookUpAdapter.updateData(lookUpListFromNetwork)
                    }
                } catch (e: Exception) {
                    this@LookupActivity.runOnUiThread {
                        Toast.makeText(this@LookupActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}