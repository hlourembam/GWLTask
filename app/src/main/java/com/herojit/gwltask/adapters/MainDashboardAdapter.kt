package com.herojit.gwl.adapters

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.herojit.gwl.viewmodels.MainDashboardViewModel
import com.herojit.gwltask.R
import com.herojit.gwltask.databinding.MaindashboardadapterBinding

class MainDashboardAdapter (
    private val context: Context,
    private var addnewcall: ArrayList<MainDashboardViewModel>
) :
    RecyclerView.Adapter<MainDashboardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: MaindashboardadapterBinding =
            DataBindingUtil.inflate(inflater, R.layout.maindashboardadapter, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return addnewcall.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(addnewcall[position])

    }


    class ViewHolder(val binding: MaindashboardadapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            dataView: MainDashboardViewModel
        ) {
            this.setIsRecyclable(false);
            binding.model = dataView

            }
        }


    }