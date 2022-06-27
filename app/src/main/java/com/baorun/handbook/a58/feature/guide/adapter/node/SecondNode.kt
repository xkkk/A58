package com.baorun.handbook.a58.feature.guide.adapter.node

import com.baorun.handbook.a58.data.ChildrenData
import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode

class SecondNode(val data: ChildrenData, override val childNode: MutableList<BaseNode>?) :BaseExpandNode() {

}