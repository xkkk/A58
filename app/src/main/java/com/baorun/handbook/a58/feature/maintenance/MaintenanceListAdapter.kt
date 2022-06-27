package com.baorun.handbook.a58.feature.maintenance

import androidx.appcompat.widget.AppCompatTextView
import com.baorun.handbook.a58.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class MaintenanceListAdapter:BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_warn_list) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.getView<AppCompatTextView>(R.id.titleTv).apply {
            text = "${holder.layoutPosition+1}.$item"
        }
    }
}