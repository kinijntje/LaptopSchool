package com.example.dnd.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spells")
data class Spell(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val level: Int,
    val school: String,
    val timeNumber: Int,
    val timeUnit: String,
    val description: String,
    val v: Boolean,
    val s: Boolean,
    val m: String? = null,
    val rangeType: String,
    val distanceType: String? = null,
    val distanceAmount: Int? = null,
    val durationUnit: String,
    val durationNumber: Int? = null,
    val damageType: String? = null,
    val savingThrow: String? = null,
    val concentration: Boolean
)