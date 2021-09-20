package com.herojit.gwltask

import androidx.multidex.MultiDexApplication
import com.herojit.gwltask.utils.SharePref


val prefs: SharePref by lazy {
    ApplicationClass.prefs!!

}

class ApplicationClass : MultiDexApplication() {

    companion object {
        var prefs: SharePref? = null
    }


    override fun onCreate() {
        super.onCreate()
        prefs = SharePref(applicationContext)
    }
}