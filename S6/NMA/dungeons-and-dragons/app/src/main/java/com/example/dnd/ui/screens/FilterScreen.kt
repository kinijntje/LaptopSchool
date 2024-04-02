@file:OptIn(ExperimentalMaterialApi::class)

package com.example.dnd.ui.screens

import androidx.annotation.StringRes
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dnd.R
import com.example.dnd.data.Filter
import com.example.dnd.ui.AppViewModelProvider
import com.example.dnd.ui.HomebrewViewModel

@Composable
fun SpellFilterPage(onConfirmPressed: (Filter) -> Unit, viewModel: HomebrewViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.TopCenter)


    QueryRows(modifier, onConfirmPressed, viewModel)
}

@Composable
fun QueryRows(modifier: Modifier = Modifier, onConfirmPressed: (Filter) -> Unit, viewModel: HomebrewViewModel) {
    val items = listOf("",
        "Abjuration", "Alteration", "Conjuration", "Divination",
        "Enchantment", "Illusion", "invocation", "Necromancy")
    val levelMin by remember { mutableStateOf(viewModel.getFilterLevelMin().toFloat()) }
    val levelMax by remember { mutableStateOf(viewModel.getFilterLevelMax().toFloat()) }
    var name by remember { mutableStateOf(viewModel.getFilterName()) }
    var school by remember { mutableStateOf(viewModel.getFilterSchool()) }

    val focusManager = LocalFocusManager.current

    var sliderValues by remember { mutableStateOf(levelMin..levelMax) }

    Box {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EditInputField(
                label = R.string.spell_name,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                value = name,
                onValueChanged = { name = it }
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "From: lvl${sliderValues.start.toInt()} to lvl${sliderValues.endInclusive.toInt()}")
            RangeSlider(value = sliderValues, onValueChange = { sliderValues_ -> sliderValues = sliderValues_}, valueRange = 0f..9f, steps = 8, modifier = Modifier.fillMaxWidth(.9f))

            Spacer(modifier = Modifier.height(15.dp))


            CustomDropdownMenu(
                modifier = modifier.fillMaxWidth(),
                items = items,
                label = R.string.spell_school,
                onValueChanged = { school = it },
                value = school
            )
        }

        Button(
            onClick = { onConfirmPressed(Filter(levelMin = sliderValues.start.toInt().toString(), levelMax = sliderValues.endInclusive.toInt().toString(), name = name, school = school)) },
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(top = 250.dp)
        ) {
            Text(text = stringResource(R.string.confirm), color = MaterialTheme.colors.secondary)
        }
    }
}

@Composable
fun EditInputField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun CustomDropdownMenu(
    modifier: Modifier = Modifier,
    items: List<String> = listOf("empty"),
    label: Int,
    onValueChanged: (String) -> Unit,
    value: String,
    returnInt: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(label) + ": ")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(MaterialTheme.colors.primary).padding(start = 10.dp)
            ) {
                Text(text = value)
                SpellItemButton(expanded = expanded, onClick = { expanded = !expanded })
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(4.dp))
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.surface)
                ) {
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        items.forEachIndexed { i, item ->
                            DropdownMenuItem(
                                onClick = {
                                    if(returnInt) onValueChanged(i.toString())
                                    else onValueChanged(item)
                                    expanded = false
                                }
                            ) {
                                Text(text = item)
                            }
                        }
                    }
                }
            }
        }
    }
}