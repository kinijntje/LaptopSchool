package com.example.dnd.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.dnd.R

@Composable
fun GamesScreen() {
    val context = LocalContext.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        WebButton(context, "https://roll20.net/welcome" , R.string.roll20)
        WebButton(context, "http://dnd5e.wikidot.com" , R.string.dnd5e)
        WebButton(context, "https://5e.tools" , R.string.dnd5etools)
    }
}

@Composable
fun WebButton(context: Context, url: String, @StringRes text: Int) {
    Button(onClick = { openWebsite(context, url) }) {
        Text(stringResource(text))
    }
}

fun openWebsite(context: Context, url: String) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}