package model


import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.paint.Color
import model.colorprovider.getRandomColor
import model.inclusions.Inclusion
import model.inclusions.InclusionType
import java.awt.geom.Point2D.distance
import java.util.*
import kotlin.collections.HashMap


object GrainGrowth{

    var size: Int = 50
    var onUpdate: (Pair<Int,Color>)->Unit = {println("dupa")}
    var board = Board(size, onUpdate)
    var neighbourhoodType = SimpleStringProperty()
    var mooreValue = SimpleIntegerProperty()
    var inclusion = Inclusion()
    var selectedGrains = HashSet<Color>()

    private var moore= {size:Int->listOf(-1, 1, -size, +size)}
    private var neuman ={size:Int-> listOf(-1, 1, -size, +size,size+1,size-1,-(size+1),-(size-1))}
    private var hex = {size:Int-> listOf(size+1,size-1,-(size+1),-(size-1))}

    private var neighbourhoodTypes = hashMapOf("Neuman" to moore,"Moore" to neuman)


    fun refreshBoard(newSize:Int = size){
        size = newSize
        board = Board(size, onUpdate)
    }

    fun drawInclusion(grainNum: Pair<Int,Int>){
        val hur = getNeighbourhoodNumber(grainNum)?.entries?.size ?: 0
        val coord = grainNum
        if(hur>1 ||  board.list[toLineCoord(grainNum.first,grainNum.second)]==Color.WHITE) {
            when (inclusion.inlucisonType) {
                InclusionType.RADIUS -> {
                    board.list.withIndex().forEach {
                        val currentCoord = to2DCoord(it.index)
                        if (Math.abs(distance(coord.first.toDouble(), coord.second.toDouble(), currentCoord.first.toDouble(), currentCoord.second.toDouble())) < inclusion.size) {
                            board.updateGrain(currentCoord, Color.BLACK)
                        }
                    }
                }
                InclusionType.SIZE -> {

                    val horizontalRange = coord.first - inclusion.size+1  until coord.first + inclusion.size
                    val veriticalRange = coord.second - inclusion.size+1  until coord.second + inclusion.size
                    board.list.withIndex().forEach {
                        val currentCoord = to2DCoord(it.index)
                        if (horizontalRange.contains(currentCoord.first) && veriticalRange.contains(currentCoord.second)) {
                            board.updateGrain(currentCoord, Color.BLACK)
                        }
                    }
                }
            }
        }
    }

    fun drawInclusion(grainNum: Int) = drawInclusion(to2DCoord(grainNum))

    fun drawBorders() {

        if (selectedGrains.isEmpty()) {
            board.list.withIndex().forEach {
                drawInclusion(it.index)
            }
        } else {
            board.list.withIndex().forEach {
                val isOnBoarder = getNeighbourhoodNumber(it.index).isNotEmpty()

                if (selectedGrains.contains(it.value)) {
                    println("" + it.value + " " + it.index + " " + selectedGrains)
                    drawInclusion(it.index % size to it.index / size)
                }
            }
        }
    }


    fun mergeSelectedGrains(){
        board.list = board.list.withIndex().map{element->
            if (selectedGrains.contains(element.value)) {
                val color = Color.PURPLE
                board.updateGrain(element.index,color)
                color
            }
            else
                element.value
        }.toTypedArray()
        selectedGrains.clear()
        selectedGrains.add(Color.PURPLE)
    }


    fun selectGrain(grainNum: Pair<Int,Int>, color:Color){
        val lineCoord = toLineCoord(grainNum.first,grainNum.second)
        if(board.list[lineCoord]==Color.WHITE)
            board.updateGrain(lineCoord,color)
        else
            selectedGrains.add(board.list[lineCoord])
    }

    fun selectRandom(number:Int){
        for(index in 0 until number){
            var rand = randomInt(0,size*size-1)

           // while((rand)){
                rand = randomInt(0,size*size-1)
           // }

            //if(rand) {
                board.updateGrain(rand, getRandomColor())
            //}
        }
    }

    fun growth() {
        board.list = board.list.withIndex().map{element->
            if (element.value == Color.WHITE && element.value!=Color.BLACK)
                if(neighbourhoodType.get() != "Neuman")
                    growGraingrowthGrainCurv(element)
                else
                    growGrain(element)
            else
                element.value
        }.toTypedArray()
        println(mooreValue)
    }



    fun reset() {
        board.list = board.list.withIndex().map{
            val color = if(selectedGrains.contains(it.value)) it.value else Color.WHITE
            onUpdate(it.index to color)
            color
        }.toTypedArray()
    }
    fun resetNonBorders() {
        board.list = board.list.withIndex().map{
            val color = if(it.value== Color.BLACK) it.value else Color.WHITE
            onUpdate(it.index to color)
            color
        }.toTypedArray()
    }

    fun fullReset() {
        board.list = board.list.withIndex().map{
            val color = Color.WHITE
            onUpdate(it.index to color)
            color
        }.toTypedArray()
        selectedGrains.clear()
    }

    fun loadBoard(newBoard:Board,newSize:Int){
        board = Board(size, onUpdate)
        size = newSize
        board.list.withIndex().forEach {
            board.list[it.index] = newBoard.list[it.index]
            onUpdate(it.index to newBoard.list[it.index])
        }
    }



    private fun growGrain(element: IndexedValue<Color>): Color{
        val maxGrain = getNeighbourhoodNumber2(element.index)
                .maxBy { it.value }
                ?.key ?: Color.WHITE

        onUpdate(element.index to maxGrain)
        return  maxGrain
    }

    private fun growGraingrowthGrainCurv(element: IndexedValue<Color>): Color{
        fun hur():Color{
            val random = Random()
            val maxGrainMoore = maxGrain(element.index, neuman(size))
            if(maxGrainMoore?.value ?: 0 >=5) return maxGrainMoore?.key ?: Color.WHITE

            val maxGrainNeuman = maxGrain(element.index, moore(size))
            if(maxGrainNeuman?.value ?: 0 >=3) return maxGrainNeuman?.key ?: Color.WHITE

            val maxGrainHex = maxGrain(element.index, hex(size))
            if(maxGrainHex?.value ?: 0 >=3) return maxGrainHex?.key ?: Color.WHITE

            val maxGrainMooreRandom = maxGrain(element.index, neuman(size))
            return if(random.nextInt(100)< mooreValue.get()) maxGrainMooreRandom?.key ?: Color.WHITE else element.value
        }


        val hur = hur()
        onUpdate(element.index to hur)
        return  hur
    }

    private fun maxGrain(index: Int, list:List<Int> ): Map.Entry<Color,Int>?{
        return getNeighbourhoodNumber2(index, list)
                .maxBy { it.value }
    }

    private fun getNeighbourhoodNumber(element: Pair<Int,Int>): Map<Color,Int>?{
        val lineElement = toLineCoord(element.first,element.second)
        return getNeighbourhoodNumber(lineElement)
    }

    private fun getNeighbourhoodNumber(element: Int,list:List<Int>? = moore(size)): Map<Color,Int>{
        return (list?:emptyList())
                .filter{element+it<size*size && element+it>0 && (Math.abs(it)!=1 || currentRow(element)== currentRow(element+it))}
                .map{board.list[element+it] }
                .filter{it!=(Color.BLACK)}
                .groupingBy({x->x})
                .eachCount()
                .filter{it.key!=Color.WHITE }
    }
    private fun getNeighbourhoodNumber2(element: Int, list:List<Int>? = moore(size)): Map<Color,Int> = getNeighbourhoodNumber(element,list).filter { !selectedGrains.contains(it.key) }

    private fun toLineCoord(x:Int,y:Int) = size*y + x
    private fun to2DCoord(x:Int) = x/size to x%size
    private fun currentRow(x:Int) = to2DCoord(x).first
    private fun randomInt(min:Int,max:Int):Int{
        val r = Random()
        return r.nextInt(max - min + 1) + min
    }
}