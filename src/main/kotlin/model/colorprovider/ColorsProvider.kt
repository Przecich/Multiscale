package model.colorprovider

import javafx.scene.paint.Color
import java.util.*

val colors = arrayListOf<Color>(
        Color.WHITE,
        Color.BLACK,
        Color.BLUE,
        Color.GREEN,
        Color.ORANGE,
        Color.YELLOW,
        Color.BROWN,
        Color.CORAL,
        Color.RED)

fun getRandomColor() = colors[(1 until colors.size).random()]

private fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) + start
