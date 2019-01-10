package model.colorprovider

import javafx.scene.paint.Color
import java.util.*


val rand = Random()


fun getRandomColor() = Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble())

fun getRandomColorList(number: Int): List<Color>{
    val list = LinkedList<Color>()

    for (i in 1..number)
        list.add(getRandomColor())

    return list
}

