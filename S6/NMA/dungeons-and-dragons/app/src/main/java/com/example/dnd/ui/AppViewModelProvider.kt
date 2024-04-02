package com.example.dnd.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dnd.DnDApplication
import com.example.dnd.MainActivity
import com.example.dnd.ui.dndSpell.SpellEntryViewModel
import com.example.dnd.ui.dndSpell.SpellViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SpellEntryViewModel(dndApplication().container.spellsRepository)
        }
        initializer {
            HomebrewViewModel()
        }
        initializer {
            SpellViewModel(dndApplication().container.spellsRepository)
        }
    }
}

fun CreationExtras.dndApplication(): DnDApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DnDApplication)