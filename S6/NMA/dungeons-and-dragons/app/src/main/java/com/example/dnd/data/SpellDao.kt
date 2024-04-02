package com.example.dnd.data

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SpellDao {
    @Query("SELECT * FROM spells " +
            "WHERE name LIKE '%' || :filterName || '%' " +
            "AND school LIKE '%' || :filterSchool || '%' " +
            "AND level >= :minLevel " +
            "AND level <= :maxLevel " +
            "ORDER BY name ASC")
    fun getAllSpellsStream(filterName: String, filterSchool: String, minLevel: String, maxLevel: String): Flow<List<Spell>>

    @Query("SELECT * from spells WHERE id = :id")
    fun getSpell(id: Int): Flow<Spell>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(spell: Spell)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(spells: List<Spell>)

    @Update
    suspend fun update(spell: Spell)

    @Delete
    suspend fun delete(spell: Spell)
}