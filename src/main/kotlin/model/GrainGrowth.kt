package model

import javafx.scene.paint.Color
import tornadofx.observable
import java.util.*

class GrainGrowth(val size: Int) {

    var moore = listOf(-1, 1, -size, +size)
    var board = Board(size)

    var onUpdate: (Pair<Int,Int>)->Unit = {}


    fun growth() {
        val growth = LinkedList<Pair<Int, Int>>()
        board.list.withIndex().forEach{element->
            if (board.list[element.index] != 0) {
                growAdjustedGrains(element,growth)
            }
        }
        growth.forEach{ pair ->
            onUpdate(pair)
            board.list[pair.first]=pair.second
        }
    }

    private fun growAdjustedGrains(element: IndexedValue<Int>, growth: LinkedList<Pair<Int, Int>>){
        moore.forEach {
            val index = element.index+it
            val isViableCell = !isBorder(index) && board.list[index] == 0

            if(isViableCell) {
                growth.add(element.index + it to board.list[element.index])
            }
        }
    }

    private fun isBorder(index:Int): Boolean{
        return index%size==0 || index%size==(size-1) || index-size<0 || index-size>(size*size-size)
    }

}