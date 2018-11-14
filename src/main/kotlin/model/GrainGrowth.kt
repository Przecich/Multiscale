package model


import javafx.beans.property.SimpleStringProperty
import javafx.scene.paint.Color
import model.colorprovider.colors
import model.inclusions.Inclusion
import model.inclusions.InclusionType
import java.awt.geom.Point2D.distance
import java.util.*



object GrainGrowth{

    var size: Int = 50
    var onUpdate: (Pair<Int,Int>)->Unit = {println("dupa")}
    var board = Board(size, onUpdate)
    var neighbourhoodType = SimpleStringProperty()
    var inclusion = Inclusion()

    private var moore= {size:Int->listOf(-1, 1, -size, +size)}
    private var neuman ={size:Int-> listOf(-1, 1, -size, +size,size+1,size-1,-(size+1),-(size-1))}

    private var neighbourhoodTypes = hashMapOf("Moore" to moore,"Neuman" to neuman)


    fun refreshBoard(newSize:Int = size){
        size = newSize
        board = Board(size, onUpdate)
    }

    fun drawInclusion(grainNum: Pair<Int,Int>){
        val hur = blabla(grainNum)?.entries?.size ?: 0
        val coord = grainNum
        if(hur>1 ||  board.list[toLineCoord(grainNum.first,grainNum.second)]==0) {
            when (inclusion.inlucisonType) {
                InclusionType.RADIUS -> {
                    board.list.withIndex().forEach {
                        val currentCoord = to2DCoord(it.index)
                        if (Math.abs(distance(coord.first.toDouble(), coord.second.toDouble(), currentCoord.first.toDouble(), currentCoord.second.toDouble())) < inclusion.size) {
                            board.updateGrain(currentCoord, colors.indexOf(Color.BLACK))
                        }
                    }
                }
                InclusionType.SIZE -> {

                    val horizontalRange = coord.first - inclusion.size+1  until coord.first + inclusion.size
                    val veriticalRange = coord.second - inclusion.size+1  until coord.second + inclusion.size
                    board.list.withIndex().forEach {
                        val currentCoord = to2DCoord(it.index)
                        if (horizontalRange.contains(currentCoord.first) && veriticalRange.contains(currentCoord.second)) {
                            board.updateGrain(currentCoord, colors.indexOf(Color.BLACK))
                        }
                    }
                }
            }
        }
    }


    fun selectGrain(grainNum: Pair<Int,Int>, color:Color){
        board.updateGrain(grainNum,colors.indexOf(color))
    }

    fun growth() {

        val growth = LinkedList<Pair<Int, Int>>()
        board.list.withIndex().forEach{element->
            if (board.list[element.index] != 0 && board.list[element.index]!=colors.lastIndex) {
                growAdjustedGrains(element,growth)
            }
        }
        growth.forEach{ pair ->
            board.updateGrain(pair.first,pair.second)
        }
    }

    fun reset() {
        board.list = board.list.withIndex().map{
            onUpdate(it.index to 0)
            0
        }.toMutableList()
    }

    fun loadBoard(newBoard:Board,newSize:Int){
        board = Board(size, onUpdate)
        size = newSize
        board.list.withIndex().forEach {
            board.list[it.index] = newBoard.list[it.index]
            onUpdate(it.index to newBoard.list[it.index])
        }
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

    private fun blabla(element: Pair<Int,Int>): Map<Int,Int>?{
        val lineElement = toLineCoord(element.first,element.second)
        return getNeighbourhood()
                ?.map{board.list[lineElement+it] }
                ?.filter{colors[it]!=(Color.BLACK)}
                ?.groupingBy({x->x})
                ?.eachCount()
    }

    private fun toLineCoord(x:Int,y:Int) = size*y + x
    private fun to2DCoord(x:Int) = x/size to x%size


}