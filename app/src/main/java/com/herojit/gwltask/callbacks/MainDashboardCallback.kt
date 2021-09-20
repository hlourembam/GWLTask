package com.herojit.gwl.callbacks

import androidx.lifecycle.LiveData
import com.google.gson.JsonObject
import org.json.JSONObject

interface MainDashboardCallback {

    fun onError(message:String)
    fun onStarted()
    fun onnetworkfailed()
    fun bindRecylerView()
    fun getJsonValue(): JSONObject?
}