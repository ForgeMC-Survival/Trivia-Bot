package com.forge.triva.data

import org.dizitart.no2.filters.Filters
import org.forgemc.api.database.DatabaseManager
import java.util.*


object UserDataManager {


    fun getUser(uuid : UUID) : User?  {
        val doc = DatabaseManager.getCollection("players")
            .find(Filters.eq("UUID", uuid.toString()))
            .firstOrNull()

        return if(doc == null) doc
        else User.fromDocument(doc)
    }

    fun getOrCreateUser(uuid: UUID): User {
        var user = getUser(uuid)

        if(user == null) {
            user = User(uuid, 0)
            DatabaseManager.getCollection("players").insert(user.asDocument())
        }

        return user
    }


}