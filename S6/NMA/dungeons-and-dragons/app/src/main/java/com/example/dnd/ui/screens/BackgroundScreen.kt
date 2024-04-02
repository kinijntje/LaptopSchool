package com.example.dnd.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.dnd.R
import java.lang.Math.toRadians
import kotlin.math.*


@Composable
fun BackgroundScreen() {
    RotatingHexagon()
}

@Composable
fun RotatingHexagon() {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing)
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val diceSize = Size(200.dp.toPx(), 200.dp.toPx())

            val xOffset = (this.size.width - diceSize.width) / 2f
            val yOffset = (this.size.height - diceSize.height) / 2f

            translate(left = xOffset, top = yOffset) {
                rotate(angle, pivot = diceSize.center) {
                    // Draw the dice outline (hexagon)
                    drawDiceOutline(diceSize)
                }
            }
        }
    }
    Column(modifier = Modifier.fillMaxWidth().padding(top = 50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(stringResource(id = R.string.NYI))
    }
}

private fun DrawScope.drawDiceOutline(size: Size) {
    val numberOfSides = 6 // A hexagon has 6 sides
    val sideLength = size.width / 2f // Adjust the side length as needed
    val angle = 360f / numberOfSides

    val centerX = size.width / 2f
    val centerY = size.height / 2f

    val path = Path()

    for (i in 0 until numberOfSides) {
        val x = (centerX + cos(toRadians(i * angle.toDouble())) * sideLength).toFloat()
        val y = (centerY + sin(toRadians(i * angle.toDouble())) * sideLength).toFloat()

        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }

    path.close()

    drawPath(
        path = path,
        color = Color.White,
        style = Stroke(width = 2.dp.toPx())
    )
}