package com.example.dnd.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dnd.data.Race
import com.example.dnd.data.races

@Composable
fun RaceScreen() {
    val modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(.93f)
        .wrapContentSize(Alignment.Center)
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        LazyColumn {
            items(races) {
                RaceCard(race = it)
            }
        }
    }
}

@Composable
fun RaceCard(race: Race, modifier: Modifier = Modifier) {
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
                RaceInformation(race.name)
            }
        }
    }
}

@Composable
fun RaceInformation(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = name,
            modifier = modifier.padding(top = 8.dp)
        )
    }
}