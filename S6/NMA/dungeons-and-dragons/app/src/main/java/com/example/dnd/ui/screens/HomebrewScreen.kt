package com.example.dnd.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dnd.R
import com.example.dnd.ui.AppViewModelProvider
import com.example.dnd.ui.dndSpell.InvalidSpellDetailsException
import com.example.dnd.ui.dndSpell.SpellEntryCheck.checkValidDetails
import com.example.dnd.ui.dndSpell.SpellEntryViewModel
import com.example.dnd.ui.dndSpell.SpellUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomebrewScreen(viewModel: SpellEntryViewModel = viewModel(factory = AppViewModelProvider.Factory), onSaveSuccessful: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    var errorMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    AddSpellBody(
        spellUiState = viewModel.spellUiState,
        onSpellValueChange = viewModel::updateUiState,
        onSaveClick = {
            try {
                checkValidDetails(viewModel.spellUiState)
                coroutineScope.launch {
                    viewModel.saveItem()
                }
                onSaveSuccessful()
            }
            catch (e: InvalidSpellDetailsException) {
                Log.d("ENTRY_ERROR", e.message.toString())
                errorMessage = e.message.toString()
                showError = true
            }
        }
    )


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LaunchedEffect(showError) {
            if (showError) {
                delay(3000) // Delay for 3 seconds
                showError = false // Reset showError to false
            }
        }

        AnimatedVisibility(
            visible = showError,
            enter = slideInVertically(initialOffsetY = { -40 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -40 }) + fadeOut()
        ) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .wrapContentSize(Alignment.BottomCenter)
                    .background(MaterialTheme.colors.surface)
            )
        }
    }
}

@Composable
fun AddSpellBody(
    spellUiState: SpellUiState,
    onSpellValueChange: (SpellUiState) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        ItemInputForm(spellUiState = spellUiState, onValueChange = onSpellValueChange)
        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ItemInputForm(
    spellUiState: SpellUiState,
    modifier: Modifier = Modifier,
    onValueChange: (SpellUiState) -> Unit = {},
    enabled: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        val focusManager = LocalFocusManager.current
        Text("Name*")
        OutlinedTextField(
            value = spellUiState.name,
            onValueChange = { onValueChange(spellUiState.copy(name = it)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            label = { Text(stringResource(R.string.name)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        EditInputField(
            label = R.string.level,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions( onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            value = spellUiState.level.toString(),
            onValueChanged = {
                if (it.isNotEmpty()) onValueChange(spellUiState.copy(level = it.toInt())) else onValueChange(spellUiState.copy(level = 0))
            }
        )
        Text("School")
        OutlinedTextField(
            value = spellUiState.school,
            onValueChange = { onValueChange(spellUiState.copy(school = it)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            label = { Text(stringResource(R.string.school)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text("Description*")
        OutlinedTextField(
            value = spellUiState.description,
            onValueChange = { onValueChange(spellUiState.copy(description = it)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            label = { Text(stringResource(R.string.description)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text("Cast Time*")
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            EditInputField(
                label = R.string.amount,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions( onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                value = spellUiState.timeNumber.toString(),
                onValueChanged = {
                    if (it.isNotEmpty()) onValueChange(spellUiState.copy(timeNumber = it.toInt())) else onValueChange(spellUiState.copy(timeNumber = 0))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = spellUiState.timeUnit,
                onValueChange = { onValueChange(spellUiState.copy(timeUnit = it)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                label = { Text(stringResource(R.string.time_unit)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                enabled = enabled,
                singleLine = true
            )
        }
        Text("Components")
        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            var vCheck by remember { mutableStateOf(false) }
            var sCheck by remember { mutableStateOf(false) }
            Text(stringResource(R.string.v_comp))
            Checkbox(checked = vCheck, onCheckedChange = { onValueChange(spellUiState.copy(v = it))
                                                            vCheck = it})
            Spacer(Modifier.width(16.dp))
            Text(stringResource(R.string.s_comp))
            Checkbox(checked = sCheck, onCheckedChange = { onValueChange(spellUiState.copy(s = it))
                                                            sCheck = it})
        }

        spellUiState.m?.let {
            OutlinedTextField(
                value = it,
                onValueChange = { onValueChange(spellUiState.copy(m = it)) },
                label = { Text(stringResource(R.string.m_comp)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
        }
        OutlinedTextField(
            value = spellUiState.rangeType,
            onValueChange = { onValueChange(spellUiState.copy(rangeType = it)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            label = { Text(stringResource(R.string.range_type)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text("Range")
        Row {
            EditInputField(
                label = R.string.amount,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions( onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                value = spellUiState.distanceAmount.toString(),
                onValueChanged = {
                    if (it.isNotEmpty()) onValueChange(spellUiState.copy(distanceAmount = it.toInt())) else onValueChange(spellUiState.copy(distanceAmount = 0))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            spellUiState.distanceType?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { onValueChange(spellUiState.copy(distanceType = it)) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    label = { Text(stringResource(R.string.distance_type)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    enabled = enabled,
                    singleLine = true
                )
            }
        }

        Text("Base Duration")
        Row {
            EditInputField(
                label = R.string.amount,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions( onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                value = spellUiState.durationNumber.toString(),
                onValueChanged = {
                    if (it.isNotEmpty()) onValueChange(spellUiState.copy(durationNumber = it.toInt())) else onValueChange(spellUiState.copy(durationNumber = 0))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = spellUiState.durationUnit,
                onValueChange = { onValueChange(spellUiState.copy(durationUnit = it)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                label = { Text(stringResource(R.string.duration_unit)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                enabled = enabled,
                singleLine = true
            )
        }
        Text("Damage Type")
        spellUiState.damageType?.let {
            OutlinedTextField(
                value = it,
                onValueChange = { onValueChange(spellUiState.copy(damageType = it)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                label = { Text("Acid") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
        }
        /* MAYBE POTENTIAL TO ADD MORE SAVING THROWS */
        Text("Saving Throws")
        spellUiState.savingThrow?.let {
            OutlinedTextField(
                value = it,
                onValueChange = { onValueChange(spellUiState.copy(savingThrow = it)) },
                label = { Text("Dexterity") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            var concentrationCheck by remember { mutableStateOf(false) }
            Text(stringResource(R.string.concentration))
            Checkbox(checked = concentrationCheck, onCheckedChange = { onValueChange(spellUiState.copy(concentration = it))
                                                                       concentrationCheck = it})
        }
    }
}
