package com.example.dnd.ui.dndSpell

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dnd.data.Spell
import com.example.dnd.data.SpellsRepository
import com.example.dnd.ui.HomebrewViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SpellViewModel(private val spellsRepository: SpellsRepository) : ViewModel() {
    private lateinit var filters: HomebrewViewModel
    lateinit var spellUiState: StateFlow<SpellListUiState>

    lateinit var name: String
    lateinit var school: String
    lateinit var minLevel: String
    lateinit var maxLevel: String


    fun setHomebrew(homebrewViewModel: HomebrewViewModel): StateFlow<SpellListUiState> {
        filters = homebrewViewModel
        name = filters.getFilterName()
        school = filters.getFilterSchool()
        minLevel = filters.getFilterLevelMin()
        maxLevel = filters.getFilterLevelMax()

        spellUiState =
        spellsRepository.getAllSpellsStream(name, school, minLevel, maxLevel).map { SpellListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = SpellListUiState()
            )
        return spellUiState
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class SpellListUiState(val spellList: List<Spell> = listOf())