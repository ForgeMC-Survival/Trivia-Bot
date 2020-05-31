package com.forge.triva

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.forgemc.api.commands.MinecraftCommand
import org.forgemc.api.player.getMenu

@MinecraftCommand(
    "TriviaTop",
    "Get the top players for Trivia",
    "/TriviaTop",
    [""]
)
class TriviaTop : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val player = sender as Player
        TriviaMenu(player.getMenu()).open()
        return false
    }
}