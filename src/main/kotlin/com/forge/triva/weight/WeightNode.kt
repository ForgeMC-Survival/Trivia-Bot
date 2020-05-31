package com.forge.triva.weight

import java.util.*

abstract class WeightNode<T>(val weight: Int) {

    abstract fun convert(random: Random): T

}
