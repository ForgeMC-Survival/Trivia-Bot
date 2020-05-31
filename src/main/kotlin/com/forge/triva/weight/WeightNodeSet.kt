package com.gravition.trivia.weight



import com.forge.triva.weight.WeightNode
import java.security.SecureRandom
import java.util.*


open class WeightNodeSet<T> {

    /**
     * All the [WeightNode]s that this set represents.
     */
    private val nodes = ArrayList<WeightNode<T>>()

    private val random = SecureRandom()

    open fun add(node: WeightNode<T>): WeightNodeSet<T> {
        nodes.add(node)
        return this
    }

    fun getRandom(random: Random = this.random): T = getRandomNode(random).convert(random)

    fun getRandomNode(random: Random = this.random): WeightNode<T> {
        val totalWeight = nodes.sumBy { it.weight }
        var randomWeight = random.nextInt(totalWeight + 1)

        for (node in nodes) {
            randomWeight -= node.weight
            if (randomWeight <= 0) {
                return node
            }
        }

        throw RuntimeException() // This will never happen.
    }

}
