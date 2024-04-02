package com.example.dnd.ui.dndSpell

class InvalidSpellDetailsException(message: String) : Exception(message)

object SpellEntryCheck {

    fun checkValidDetails(uiState: SpellUiState): Boolean {
        if (uiState.name.isEmpty()) throw InvalidSpellDetailsException("Name must not be empty")

        if (uiState.description.isEmpty()) throw InvalidSpellDetailsException("Description must not be empty")

        if (uiState.level.toString().isEmpty() || uiState.level < 0) throw InvalidSpellDetailsException("Level must not be empty and be positive")

        if (uiState.timeNumber.toString().isEmpty() || uiState.timeNumber < 0) throw InvalidSpellDetailsException("Time amount must not be empty and be positive")

        if (uiState.timeUnit.isEmpty()) throw InvalidSpellDetailsException("Time unit must not be empty")

        if (uiState.distanceAmount!! < 0) throw InvalidSpellDetailsException("Distance amount must be positive")

        if (uiState.durationNumber!! < 0) throw InvalidSpellDetailsException("Duration amount must be positive")
        return true
    }
}
