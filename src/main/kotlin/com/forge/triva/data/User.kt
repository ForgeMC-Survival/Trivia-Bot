package com.forge.triva.data

import org.dizitart.no2.Document
import org.forgemc.api.database.DatabaseSerialisable
import org.forgemc.api.database.getBoolean
import org.forgemc.api.database.getInt
import org.forgemc.api.database.getString
import java.util.*

data class User(var UUID: UUID, var answered: Int) : DatabaseSerialisable {

    companion object {
        fun fromDocument(doc: Document) = User(
            UUID.fromString(doc.getString("UUID")),
            doc.getInt("correct")
        )
    }

    override fun asDocument() = Document.createDocument("UUID", UUID.toString())
        .put("correct", answered)!!

}


