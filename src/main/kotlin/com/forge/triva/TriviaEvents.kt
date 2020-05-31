package com.forge.triva

import com.forge.triva.data.Configuration.data
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.forgemc.api.events.MinecraftEvent
import com.forge.triva.TriviaStart.Companion.activeTriviaSet
import com.forge.triva.TriviaStart.Companion.plugin
import com.forge.triva.data.UserDataManager.getOrCreateUser
import org.bukkit.Material
import org.bukkit.event.EventPriority
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.inventory.ItemStack
import org.dizitart.no2.filters.Filters
import org.forgemc.api.database.DatabaseManager
import org.forgemc.api.player.*

@MinecraftEvent("TrivaEvents")
class TriviaEvents : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if(Bukkit.getOnlinePlayers().isNotEmpty() && activeTriviaSet == -1) {
            delayByWait(plugin, toSeconds(data.triviaDelay) , toSeconds(data.triviaDelay)) {
                activeTriviaSet = Trivia.getRandomTriviaSet()
                for (player in Bukkit.getOnlinePlayers()) {
                    player.message(data.message.replace("{question}", data.questions[activeTriviaSet].question))
                }
                runOnce(plugin,toSeconds(data.answertime)) {
                    event.player.message(data.noanswer)
                    activeTriviaSet = -1
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerChat(event : AsyncPlayerChatEvent)  {
        if(Bukkit.getOnlinePlayers().isNotEmpty() && activeTriviaSet != -1) {
            if (data.questions[activeTriviaSet].answers.contains(event.message.toLowerCase())) {
                val reward = TriviaStart.rewardSet.getRandom()
                val user = getOrCreateUser(event.player.uniqueId)
                user.answered += 1

                val item  = ItemStack(Material.getMaterial(reward.item),reward.amount)
                event.player.inventory.addItem(item)

                Bukkit.getOnlinePlayers().forEach {
                    it.message(data.correctMessageGlobal)
                }

                event.player.message(data.correctMessage.
                    replace("{name}", event.player.displayName).
                    replace("{item}",item.data.itemType.name).
                    replace("{amount}", item.amount.toString()).
                    replace("{prefix}",data.prefix)
                )

                if(user.answered.rem(data.specialRewardAmount) == 0) {
                    if(data.special.size != 0) {
                        val reward : Int = (0 until data.special.size).shuffled().last()
                        val amt : Int = (data.special[reward].range.formatBefore() until data.special[reward].range.formatAfter()).shuffled().last()
                        val item1  = ItemStack(Material.getMaterial(data.special[reward].item),amt)
                        event.player.inventory.addItem(item1)
                        if(data.specialReward == "") {
                            event.player.sendMessage(data.specialReward.
                                replace("{name}", event.player.displayName).
                                replace("{item}", item1.data.itemType.name).
                                replace("{amount}",item1.amount.toString()).
                                replace("{prefix}",data.prefix))
                        }
                    }
                    DatabaseManager.getCollection("players").update(Filters.eq("UUID",
                        event.player.uniqueId.toString()),
                        user.asDocument()
                    )
                }
                activeTriviaSet = -1
            }
        }
    }

}