package com.example.dnd.ui.dndSpell

import com.example.dnd.data.Spell

data class SpellUiState(
    val id: Int = 0,
    val name: String = "",
    val level: Int = 0,
    val school: String = "",
    val timeNumber: Int = 0,
    val timeUnit: String = "action",
    val description: String = "",
    val v: Boolean = false,
    val s: Boolean = false,
    val m: String? = "",
    val rangeType: String = "",
    val distanceType: String? = "",
    val distanceAmount: Int? = 0,
    val durationUnit: String = "",
    val durationNumber: Int? = 0,
    val damageType: String? = "",
    val savingThrow: String? = "",
    val concentration: Boolean = false,
    val actionEnabled: Boolean = false
)

fun SpellUiState.toSpell(): Spell = Spell(
    id = id,
    name = name,
    level = level,
    school = school,
    timeNumber = timeNumber,
    timeUnit = timeUnit,
    description = description,
    v = v,
    s = s,
    m = m,
    rangeType = rangeType,
    distanceType = distanceType,
    distanceAmount = distanceAmount,
    durationUnit = durationUnit,
    durationNumber = durationNumber,
    damageType = damageType,
    savingThrow = savingThrow,
    concentration = concentration
)

fun Spell.toSpellUiState(actionEnabled: Boolean = false): SpellUiState = SpellUiState(
    id = id,
    name = name,
    level = level,
    school = school,
    timeNumber = timeNumber,
    timeUnit = timeUnit,
    description = description,
    v = v,
    s = s,
    m = m,
    rangeType = rangeType,
    distanceType = distanceType,
    distanceAmount = distanceAmount,
    durationUnit = durationUnit,
    durationNumber = durationNumber,
    damageType = damageType,
    savingThrow = savingThrow,
    concentration = concentration,
    actionEnabled = actionEnabled
)

/* UPDATE VALID CHECK */
fun SpellUiState.isValid() : Boolean {
    return name.isNotBlank() && description.isNotBlank()
}
