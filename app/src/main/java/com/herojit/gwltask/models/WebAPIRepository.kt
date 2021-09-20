package com.herojit.gwltask.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebAPIRepository {

    //Get Method
    fun getAPIStringCall(flag: String): LiveData<String> {
        val loginResponse = MutableLiveData<String>()
        var URL: String = ""
        when (flag) {
            "Dashboard" -> {
                URL = APIUrls.URL_Dashboard
            }
        }

        APIServiceProvider().getStringAPI(URL)
            .enqueue(object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    loginResponse.value = t.message
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        loginResponse.value = response.body()?.toString()
                    } else {
                        loginResponse.value = response.errorBody()?.toString()
                    }
                }
            })

        return loginResponse
    }
}