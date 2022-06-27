package com.baorun.handbook.a58.feature.maintenance

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baorun.handbook.a58.data.ChildrenData
import com.baorun.handbook.a58.data.DataManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MaintenanceViewModel:ViewModel() {
    val maintenanceLiveData = MutableLiveData<ChildrenData>()
    fun getData(id:String){
        viewModelScope.launch(Dispatchers.IO){
            DataManager.getMaintenanceDataFromJson(id).collect {
                it?.let {
                    maintenanceLiveData.postValue(it)
                }
            }
        }
    }
}