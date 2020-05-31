package com.forge.triva

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.forgemc.api.database.DatabaseManager
import org.forgemc.api.ui.gui.InventorySlot
import org.forgemc.api.ui.gui.Menu
import org.forgemc.api.ui.gui.PlayerMenu
import java.util.*
import java.util.stream.Collectors


class TriviaMenu(playermenu: PlayerMenu) : Menu(
    "&4&lTrivia Top",
    1,
    playermenu
) {

    override var filler: ItemStack = ItemStack(Material.BARRIER, 1)

    override var items: ArrayList<InventorySlot> = arrayListOf()

    override fun handleClose(event: InventoryCloseEvent) {

    }

    override fun handleMenu(event: InventoryClickEvent) {

    }

    override fun handleOpen(event: InventoryOpenEvent) {

        var index = -1
        DatabaseManager.getCollection("players").find().forEach {
            index++
            println(it.toString())
            items.add(InventorySlot(
                "${Bukkit.getPlayer(UUID.fromString(it.get("UUID").toString())).name}",
                getPlayerHead(it.get("UUID").toString()),
                listOf("Total Correct: ${it.get("correct")}"),index)
            )
        }
        items.forEach {
            inventory.setItem(it.slot, createGuiItem(it))
        }
    }


    private fun getPlayerHead(owner: String): ItemStack {

        val newVersion = Arrays.stream(Material.values()).map { it.name }.collect(Collectors.toList()).contains("PLAYER_HEAD")

        val type = Material.matchMaterial(
            if(newVersion) "PLAYER_HEAD" else "SKULL_ITEM"
        )

        val item = ItemStack(type,1)

        if(!newVersion) {
            item.durability = 3 as Short
        }

        val meta = item.itemMeta as SkullMeta
        meta.owningPlayer = Bukkit.getPlayer(UUID.fromString(owner))
        item.itemMeta = meta
        return item
    }

}