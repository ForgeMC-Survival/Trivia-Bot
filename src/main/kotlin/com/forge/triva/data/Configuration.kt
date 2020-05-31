package com.forge.triva.data

import com.forge.triva.TriviaStart.Companion.plugin
import com.google.gson.GsonBuilder
import mu.KotlinLogging
import java.io.File


data class Settings(
    val triviaDelay: Int = 30,
    val answertime : Int = 1,
    val specialRewardAmount : Int = 10,
    val prefix : String = "[HEY]",
    val specialReward : String = "{prefix}{name}{item}{amount}",
    val correctMessage : String = "{prefix}{name}{item}{amount}",
    val message : String = "{prefix}{question}",
    val noanswer : String = "{prefix} No Answer",
    val norights : String = "No Rights go away",
    val correctMessageGlobal : String = "The Question has been answerd check /triviatop",
    val items : MutableList<Item> = listOf(
        Item(
            "OAK_LOG",
            "1...2",
            "COMMON"
        )
    ).toMutableList(),
    val special : MutableList<SpecialItem> = listOf(
        SpecialItem(
            "OAK_LOG",
            "1...2"
        )
    ).toMutableList(),
    val questions : MutableList<Questions> = listOf(
        Questions(
            "Has beef released a a selfie",
            mutableListOf("yes", "yup")
        )
    ).toMutableList()
)

data class Item(val item : String,val range : String,val rarity : String)

data class SpecialItem(val item : String,val range : String)

data class Questions(val question : String,val answers : MutableList<String> = listOf("yes","Yup").toMutableList())


object Configuration {

    /**
     *  [log] for this class
     */
    val log = KotlinLogging.logger {}
    /**
     *  [gson] Gson Builder
     */
    val gson = GsonBuilder().setPrettyPrinting().create()
    /**
     *  [data] Data for settings
     */
    lateinit var data : Settings

    /**
     *  [load] Loads the file into memory
     */
    fun load() {
        log.info { "Loading Settings" }
        val file = File(plugin.dataFolder,"configs.json")
        if(!file.exists()) {
            log.info { "No File found Writing" }
            file.createNewFile()
            file.writeText(gson.toJson(Settings()))
        }
        data = gson.fromJson(file.readText(), Settings::class.java)
        log.info { "Finished Loading Settings" }

    }

}