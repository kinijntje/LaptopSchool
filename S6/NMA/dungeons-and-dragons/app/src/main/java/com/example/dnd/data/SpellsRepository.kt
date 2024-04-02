package com.example.dnd.data

import kotlinx.coroutines.flow.Flow

interface SpellsRepository {
    fun getAllSpellsStream(name: String, school: String, minLevel: String, maxLevel: String): Flow<List<Spell>>

    fun getSpellStream(id: Int): Flow<Spell?>

    suspend fun insertSpell(spell: Spell)

    suspend fun deleteSpell(spell: Spell)

    suspend fun updateSpell(spell: Spell)
}