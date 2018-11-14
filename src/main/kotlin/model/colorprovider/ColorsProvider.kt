package model.colorprovider

import javafx.scene.paint.Color
import java.util.*

val colors = arrayListOf<Color>(
        Color.WHITE,
        Color.BLUE,
        Color.GREEN,
        Color.ORANGE,
        Color.YELLOW,
        Color.BROWN,
        Color.CORAL,
        Color.RED,
        Color.BLACK
)

fun getRandomColor() = colors[(1 until colors.size-1).random()]

private fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) + start
