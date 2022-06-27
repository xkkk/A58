package com.baorun.handbook.a58.feature.guide.adapter.node

import com.baorun.handbook.a58.data.ChildrenData
import com.chad.library.adapter.base.entity.node.BaseNode

class ForthNode(val data: ChildrenData) : BaseNode() {
    override val childNode: MutableList<BaseNode>?
        get() = null
}