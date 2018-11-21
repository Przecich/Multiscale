package model.colorprovider

import javafx.scene.paint.Color
import java.util.*


val rand = Random()

fun getRandomColor() = Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble())

