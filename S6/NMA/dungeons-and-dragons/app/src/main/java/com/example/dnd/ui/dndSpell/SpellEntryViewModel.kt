package com.example.dnd.ui.dndSpell

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dnd.data.SpellsRepository

class SpellEntryViewModel(private val spellsRepository: SpellsRepository) : ViewModel() {
    var spellUiState by mutableStateOf(SpellUiState())
        private set
    fun updateUiState(newSpellUiState: SpellUiState) {
        spellUiState = newSpellUiState.copy(actionEnabled = newSpellUiState.isValid())
    }

    suspend fun saveItem() {
        if (spellUiState.isValid()) {
            spellsRepository.insertSpell(spellUiState.toSpell())
        }
    }
}