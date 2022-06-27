package com.baorun.handbook.a58.feature.vision

import androidx.lifecycle.ViewModel
import com.baorun.handbook.a58.data.DataManager
import com.baorun.handbook.a58.data.Hotspot

class HotspotViewModel : ViewModel() {

    fun getVisionIn1HotspotList(): List<Hotspot> {
        return DataManager.getVisionIn1HotspotList()
    }

    fun getVisionIn2HotspotList(): List<Hotspot> {
        return DataManager.getVisionIn2HotspotList()
    }

    fun getVisionOut1HotspotList(): List<Hotspot> {
        return DataManager.getVisionOut1HotspotList()
    }

    fun getVisionOut2HotspotList(): List<Hotspot> {
        return DataManager.getVisionOut2HotspotList()
    }
}