package com.forge.triva.weight.impl

import com.gravition.trivia.weight.WeightNodeSet
import com.forge.triva.weight.WeightNode

class WeightItemSet : WeightNodeSet<Item>() {

    override fun add(node: WeightNode<Item>): WeightItemSet {
        super.add(node)
        return this
    }

}