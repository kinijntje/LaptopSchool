package com.example.dnd.data

import android.content.Context

interface AppContainer {
    val spellsRepository: SpellsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val spellsRepository: SpellsRepository by lazy {
        OfflineSpellsRepository(DnDDatabase.getDatabase(context).spellDao())
    }
}