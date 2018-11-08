package model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.paint.Color
import tornadofx.observable
import java.util.*
import kotlin.collections.HashMap

object GrainGrowth{

    var size: Int = 50
    var board = Board(size)
    var onUpdate: (Pair<Int,Int>)->Unit = {}
    var neighbourhoodType = SimpleStringProperty()

    private var moore= {size:Int->listOf(-1, 1, -size, +size)}
    private var neuman ={size:Int-> listOf(-1, 1, -size, +size,size+1,size-1,-(size+1),-(size-1))}

    private var neighbourhoodTypes = hashMapOf("Moore" to moore,"Neuman" to neuman)


    fun refreshBoard(newSize:Int = size){
        size = newSize
        board = Board(size)
    }

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

    fun reset() {
        board.list = board.list.withIndex().map{
            onUpdate(it.index to 0)
            0
        }.toMutableList()
    }

    private fun growAdjustedGrains(element: IndexedValue<Int>, growth: LinkedList<Pair<Int, Int>>){
        getNeighbourhood()?.forEach {
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

    private fun getNeighbourhood(): List<Int>?{
        return neighbourhoodTypes[neighbourhoodType.get()]?.invoke(size)
    }

}