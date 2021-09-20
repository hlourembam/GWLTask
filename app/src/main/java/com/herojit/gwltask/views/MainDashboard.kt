package com.herojit.gwl.views

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.herojit.gwl.viewmodels.MainDashboardViewModel
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.herojit.gwl.adapters.MainDashboardAdapter
import com.herojit.gwl.callbacks.MainDashboardCallback
import com.herojit.gwltask.R
import com.herojit.gwltask.databinding.MaindashboardBinding
import com.herojit.gwltask.prefs
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.ArrayList

class MainDashboard : AppCompatActivity(), MainDashboardCallback {

    lateinit var databinding: MaindashboardBinding
    lateinit var viewModel: MainDashboardViewModel
    private var jsonvalueResponse: JSONObject? = null
    private var adapter: MainDashboardAdapter? = null

    internal var REQUEST_CODE = 1
    private val TAG = "Permission"
    var PermissionList = arrayOf(
        Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding =
            DataBindingUtil.setContentView<MaindashboardBinding>(this, R.layout.maindashboard)
        viewModel = ViewModelProvider(this).get(MainDashboardViewModel::class.java)
//        val viewModel = ViewModelProviders.of(this).get(AddNewCallViewModel::class.java)
        databinding.model = viewModel
        viewModel.listener = this

        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            setPermission()
        }

        bindRecylerView();


    }

    override fun bindRecylerView() {

        databinding.recyclerView.visibility = View.GONE
        databinding.txtNodata.visibility = View.GONE
        databinding.progressbar.visibility = View.GONE
        try {
            viewModel.getRecylcerViewData()!!.observe(this, Observer {
                databinding.progressbar.visibility = View.GONE
                val value: String = it;

                jsonvalueResponse = JSONObject(value)
                if (jsonvalueResponse != null && jsonvalueResponse!!.length() > 0) {
                    jsonvalueResponse = JSONObject(value)
                    databinding.recyclerView.visibility = View.VISIBLE
                    bindrecyc()
                } else {
                    databinding.txtNodata.visibility = View.VISIBLE
                }
            })
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    fun bindrecyc() {
        try {
            viewModel.bindRecylceView().observe(this, Observer { viewModel ->
                databinding.recyclerView.visibility = View.VISIBLE
                adapter = MainDashboardAdapter(this, viewModel!!)
                databinding.recyclerView!!.setLayoutManager(LinearLayoutManager(this))
                databinding.recyclerView!!.setAdapter(adapter)

            })
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onStarted() {
        databinding.progressbar.visibility = View.VISIBLE
    }

    override fun onnetworkfailed() {
        databinding.progressbar.visibility = View.GONE
        Toast.makeText(this, resources.getString(R.string.Networkfailed), Toast.LENGTH_LONG).show()
    }

    override fun onError(message: String) {
        databinding.progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun getJsonValue(): JSONObject? {
        return jsonvalueResponse!!;
    }

    private fun setPermission() {
        val permission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission denied")
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.INTERNET
            )
        ) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Permission to be Access for Internet")
            builder.setMessage("Permission Required")
            builder.setPositiveButton("OK") { dialog, which ->
                Log.d(TAG, "Clicked")
                makeRequest()
            }
            val dialog = builder.create()
            dialog.show()
        } else {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, PermissionList, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)) {
                    Log.d(TAG, "Permission had been denied by user")
                } else {
                    Log.d(TAG, "Permission had been granted by user")
                }
                return
            }
            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

}