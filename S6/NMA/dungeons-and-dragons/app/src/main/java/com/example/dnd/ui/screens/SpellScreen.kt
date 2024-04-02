package com.example.dnd.ui.screens

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dnd.data.Spell
import com.example.dnd.R
import com.example.dnd.ui.AppViewModelProvider
import com.example.dnd.ui.HomebrewViewModel
import com.example.dnd.ui.dndSpell.SpellViewModel

@Composable
fun SpellScreen(
    onFilterPressed: () -> Unit, onAddPressed: () -> Unit,
    viewModel: SpellViewModel = viewModel(factory = AppViewModelProvider.Factory),
    homebrewViewModel: HomebrewViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.93f)
        .wrapContentSize(Alignment.Center)
    viewModel.setHomebrew(homebrewViewModel)
    val spellUiState by viewModel.spellUiState.collectAsState()
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        Spacer(modifier = Modifier.width(50.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = onAddPressed) {
                Image(painter = painterResource(R.drawable.add_48px), contentDescription = "add", modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(150.dp))
            Button(onClick = onFilterPressed) {
                Image(painter = painterResource(R.drawable.filter_list_48px), contentDescription = "filter", modifier = Modifier.size(24.dp))
            }
        }
        LazyColumn {
            items(items = spellUiState.spellList, key = { it.id }) {
                SpellItem(spell = it)
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun SpellItem(spell: Spell, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.padding(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                SpellInformation(spell)
                Spacer(Modifier.weight(1f))
                SpellItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                )
            }
            if (expanded) {
                SpellDetail(spell.description)
            }
        }
    }
}

@Composable
fun SpellInformation(spell: Spell, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = spell.name,
            modifier = modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.h6
        )
        Row {
            Text(
                text = stringResource(R.string.level_input, spell.level),
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.width(35.dp))
            Text(
                text = spell.school,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Composable
fun SpellDetail(description: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(
            start = 16.dp,
            top = 8.dp,
            bottom = 16.dp,
            end = 16.dp
        )
    ) {
        Text(
            text = stringResource(R.string.description),
            style = MaterialTheme.typography.caption
        )
        Text(
            text = description,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun SpellItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            tint = MaterialTheme.colors.secondary,
            contentDescription = stringResource(R.string.expand_button_content_description),
        )
    }
}