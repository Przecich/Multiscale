package model

import javafx.scene.paint.Color
import tornadofx.observable
import java.util.*

class GrainGrowth(val width: Int, val height: Int) {
    var list = IntArray(width * height).toMutableList()
    var moore = listOf(-1, 1, -width, +width)

    val observers = ArrayList<(Pair<Int,Int>)->Unit>()


    fun growth() {
        val growth = LinkedList<Pair<Int, Int>>()
        list.withIndex().forEach{element->
            if (list[element.index] != 0) {
                growAdjustedGrains(element,growth)
            }
        }
        growth.forEach{ pair ->
            observers.forEach{it(pair)}
            list[pair.first]=pair.second
        }
    }

    private fun growAdjustedGrains(element: IndexedValue<Int>, growth: LinkedList<Pair<Int, Int>>){
        moore.forEach {
            val index = element.index+it
            val isViableCell = !isBorder(index) && list[index] == 0

            if(isViableCell) {
                growth.add(element.index + it to list[element.index])
            }
        }
    }

    private fun isBorder(index:Int): Boolean{
        return index%width==0 || index%width==(width-1) || index-width<0 || index-width>(height*width-width)
    }

}