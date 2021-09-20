package com.herojit.gwl.viewmodels

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.herojit.gwl.callbacks.MainDashboardCallback
import com.herojit.gwltask.entities.DashboardEntity
import com.herojit.gwltask.models.WebAPIRepository
import com.herojit.gwltask.prefs
import com.wrms.wwfap.models.networks.ApiException
import com.wrms.wwfap.models.networks.NoInternetException
import org.json.JSONObject
import kotlin.collections.ArrayList

class MainDashboardViewModel : ViewModel {

    var listener: MainDashboardCallback? = null

    var liveDataMaster: LiveData<String>? = null
    var arraylist = ArrayList<MainDashboardViewModel>()
    var arraydata = MutableLiveData<ArrayList<MainDashboardViewModel>>()

    var ID = ""
    var UserID = ""
    var Title = ""
    var Body = ""

    //    fun MainDashboardViewModel() {
//        // trigger user load.
//    }
//    fun MainDashboardViewModel(addcall: DashboardEntity) {
//        this.ID = addcall.id
//        this.UserID = addcall.user_id
//        this.Title = addcall.title
//        this.Body = addcall.body
//    }

    constructor() : super()
    constructor(addcall: DashboardEntity) : super() {

        this.ID = addcall.id
        this.UserID = addcall.user_id
        this.Title = addcall.title
        this.Body = addcall.body
    }

//    constructor(addcall: DashboardEntity) {
//        this.ID = addcall.id
//        this.UserID = addcall.user_id
//        this.Title = addcall.title
//        this.Body = addcall.body
//    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun getRecylcerViewData(
    ): LiveData<String>? {
        try {
            if (prefs!!.networkCheck()) {
                listener?.onStarted()
                liveDataMaster =
                    WebAPIRepository().getAPIStringCall("Dashboard")
            } else {
                listener?.onnetworkfailed()
            }
        } catch (e: ApiException) {
            listener?.onError(e.message!!)
        } catch (e: NoInternetException) {
            listener?.onError(e.message!!)
        }
        return liveDataMaster
    }

    fun onRefreshClick(v: View) {
        listener?.bindRecylerView()
    }


    fun bindRecylceView(): MutableLiveData<ArrayList<MainDashboardViewModel>> {

        val values: JSONObject? = listener?.getJsonValue();
        if (values != null && values.length() > 0) {
            try {
                arraylist = ArrayList<MainDashboardViewModel>()
                arraydata = MutableLiveData<ArrayList<MainDashboardViewModel>>()
                val jsonArray = values.getJSONArray("data")
                if (jsonArray.length() > 0) {
                    for (i in 0 until jsonArray.length()) {
                        val obj = JSONObject(jsonArray.get(i).toString())
                        val callentity = DashboardEntity(
                            obj.getInt("id").toString(),
                            obj.getInt("user_id").toString(),
                            obj.getString("title"),
                            obj.getString("body")
                        )
                        val addnew: MainDashboardViewModel = MainDashboardViewModel(callentity)
                        arraylist!!.add(addnew)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        } else {
            arraylist = ArrayList<MainDashboardViewModel>()
            arraydata = MutableLiveData<ArrayList<MainDashboardViewModel>>()
        }
        arraydata.value = arraylist
        return arraydata
    }


}