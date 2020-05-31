package com.forge.triva

import com.forge.triva.TriviaStart.Companion.rewardSet
import com.forge.triva.data.Configuration
import com.forge.triva.weight.Weights
import com.forge.triva.weight.impl.WeightItem
import com.forge.triva.weight.impl.WeightItemSet

object Trivia {

    fun getRandomTriviaSet(): Int {
        return (0 until Configuration.data.questions.size).shuffled().last()
    }


    fun createItems() {
        val rewards : MutableList<WeightItem> = ArrayList()

        Configuration.data.items.forEach {
            val weighted = WeightItem(
                it.item,
                it.range.formatBefore()..it.range.formatAfter(),
                Weights.valueOf(it.rarity.toUpperCase()).weight
            )
            rewards.add(weighted)
        }

        rewardSet = WeightItemSet().apply {
            rewards.forEach { reward ->
                add(reward)
            }
        }
    }
}


fun String.formatBefore() : Int {
    return this.substringBefore(".").replace(".","").toInt()
}

fun String.formatAfter() : Int {
    return this.substringAfterLast(".").replace(".","").toInt()
}