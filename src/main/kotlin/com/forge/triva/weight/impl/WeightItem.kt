package com.forge.triva.weight.impl

import com.forge.triva.weight.WeightNode
import java.util.*


data class  Item(val item: String,val amount: Int)

open class WeightItem(val item: String, val amount: Int = 1, private val maxAmount: Int = amount, weight: Int) : WeightNode<Item>(weight) {

    constructor(item: String, amount: IntRange, weight: Int) : this(item, amount.first, amount.last, weight)

    override fun convert(random: Random): Item = Item(item, amount + random.nextInt((maxAmount - amount) + 1))

}
