package com.example.dnd.data

import android.content.Context
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dnd.R

@Database(entities = [Spell::class], version = 1, exportSchema = false)
abstract class DnDDatabase : RoomDatabase() {
    abstract fun spellDao(): SpellDao
    companion object {
        @Volatile
        private var Instance : DnDDatabase? = null

        fun getDatabase(context: Context): DnDDatabase {
            Log.d("DnDDatabase", "Creating database")
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DnDDatabase::class.java, "dnd_database")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("database/dndv2.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}