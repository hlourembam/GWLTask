package com.herojit.gwltask.utils

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.wrms.wwfap.models.networks.NetworkConnectionInterceptor
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SharePref(context: Context) {
    private val applicationContext = context.applicationContext

    @RequiresApi(Build.VERSION_CODES.M)
    fun networkCheck(): Boolean {
        var check: Boolean = false
        val networkconnection: NetworkConnectionInterceptor =
            NetworkConnectionInterceptor(applicationContext)
        if (networkconnection.isInternetAvailable() == true) {
            check = true
        }
        return check
    }
    fun setToast(context: Context, SMS: String, flag: Int) {
        when (flag) {
            0 -> {//Short
                Toast.makeText(
                    context,
                    SMS,
                    Toast.LENGTH_SHORT
                ).show()
            }
            1 -> {//Long
                Toast.makeText(
                    context,
                    SMS,
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

}