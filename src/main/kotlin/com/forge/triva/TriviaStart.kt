package com.forge.triva

import com.forge.triva.Trivia.createItems
import com.forge.triva.data.Configuration
import com.forge.triva.weight.impl.WeightItemSet
import org.bukkit.plugin.java.JavaPlugin
import org.forgemc.api.ForgePlugin

class TriviaStart : ForgePlugin() {

    companion object {

        lateinit var  plugin : JavaPlugin

        /**
         *  [activeTriviaSet] If the Trivia is running a game
         */
        var activeTriviaSet : Int = -1


        /**
         *  [rewardSet] Reward Set
         */
        var  rewardSet = WeightItemSet()


    }


    /**
     * Called when the plugin is enabled
     */
    override fun start() {
        plugin = this
        Configuration.load()
        createItems()
    }

    /**
     * Called when the plugin is disabled
     */
    override fun stop() {

    }



}
