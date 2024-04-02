package com.example.dnd.data

import android.util.Log
import com.example.dnd.ui.HomebrewViewModel
import kotlinx.coroutines.flow.Flow

class OfflineSpellsRepository(private val spellDao: SpellDao) : SpellsRepository {
    override fun getAllSpellsStream(name: String, school: String, minLevel: String, maxLevel: String): Flow<List<Spell>> {
        return spellDao.getAllSpellsStream(name, school, minLevel, maxLevel)
    }
    override fun getSpellStream(id: Int): Flow<Spell?> = spellDao.getSpell(id)
    override suspend fun insertSpell(spell: Spell) = spellDao.insert(spell)
    override suspend fun deleteSpell(spell: Spell) = spellDao.delete(spell)
    override suspend fun updateSpell(spell: Spell) = spellDao.update(spell)
}